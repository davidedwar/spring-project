package com.homecooked.common.product;

import com.homecooked.common.addons.AddOns;
import com.homecooked.common.addons.AddOnsRepository;
import com.homecooked.common.addons.dto.AddOnsCreateUpdateDto;
import com.homecooked.common.auth.AuthService;
import com.homecooked.common.chef.Chef;
import com.homecooked.common.chef.ChefService;
import com.homecooked.common.exception.DataBaseException;
import com.homecooked.common.product.dto.ProductCreateDto;
import com.homecooked.common.product.dto.ProductResponseDto;
import com.homecooked.common.product.mapper.ProductMapper;
import com.homecooked.common.product.projection.ProductSummaryProjection;
import com.homecooked.common.product.specification.ProductSpecifications;
import com.homecooked.common.productcategory.ProductCategory;
import com.homecooked.common.productcategory.ProductCategoryRepository;
import com.homecooked.common.productspecification.ProductSpecification;
import com.homecooked.common.productspecification.ProductSpecificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final AddOnsRepository addOnsRepository;
    private final ProductMapper productMapper;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ProductSpecificationRepository productSpecificationRepository;
    private final AuthService authService;
    private final ChefService chefService;

    public void addProduct(ProductCreateDto createDto) {
        Chef chef = chefService.currentChef();
        ProductCategory category = productCategoryRepository.findById(createDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Product category not found"));

        Product product = productMapper.dtotoEntity(createDto);
        product.setChef(chef);
        product.setCategory(category);

        try {
            product = productRepository.save(product);
        } catch (DataAccessException ex) {
            throw new DataBaseException("Unable to save product");
        }

        LocalDateTime now = LocalDateTime.now();
        for (AddOnsCreateUpdateDto specDto : createDto.getSpecifications()) {
            AddOns addOns = AddOns.builder()
                    .name(specDto.name())
                    .additionalCost(specDto.additionalCost())
                    .description(specDto.description())
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            AddOns savedAddOns = addOnsRepository.save(addOns);

            ProductSpecification productSpecification = ProductSpecification.builder()
                    .product(product)
                    .addOns(savedAddOns)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            productSpecificationRepository.save(productSpecification);
        }
    }


    public ProductResponseDto updateProduct(Integer id, ProductCreateDto updateDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product category not found"));

        if (updateDto.getSpecifications() != null) {
            updateProductSpecifications(product, updateDto.getSpecifications());
        }

        productMapper.updateEntityFromDto(updateDto, product);
        productRepository.save(product);
        return productMapper.toResponseDto(product);
    }

    private void updateProductSpecifications(Product product, List<AddOnsCreateUpdateDto> specDtos) {
        List<ProductSpecification> currentSpecs = productSpecificationRepository.findByProductId(product.getId());

        Map<String, ProductSpecification> currentSpecMap = currentSpecs.stream()
                .collect(Collectors.toMap(spec -> spec.getAddOns().getName(), Function.identity()));

        for (AddOnsCreateUpdateDto dto : specDtos) {
            if (currentSpecMap.containsKey(dto.name())) {
                AddOns spec = currentSpecMap.get(dto.name()).getAddOns();
                spec.setName(dto.name());
                spec.setAdditionalCost(dto.additionalCost());
                spec.setDescription(dto.description());
                addOnsRepository.save(spec);
            } else {
                AddOns newSpec = AddOns.builder()
                        .name(dto.name())
                        .additionalCost(dto.additionalCost())
                        .description(dto.description())
                        .build();
                AddOns savedSpec = addOnsRepository.save(newSpec);

                ProductSpecification newProductSpec = ProductSpecification.builder()
                        .product(product)
                        .addOns(savedSpec)
                        .build();
                productSpecificationRepository.save(newProductSpec);
            }
        }

    }

    public ProductResponseDto viewProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with given id not found"));

        return productMapper.toResponseDto(product);
    }

    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with given id not found"));

        productRepository.delete(product);
    }

    public Page<ProductSummaryProjection> getAllChefProducts(Pageable pageable) {
        Integer chefId = authService.getCurrentChef().getId();
        return productRepository.findAllByChefId(chefId, pageable);
    }

    public Page<Product> searchProducts(String name, String chefName, Pageable pageable) {
        Specification<Product> spec = Specification.where(null);

        if (name != null) spec = spec.and(ProductSpecifications.hasNameLike(name));
        if (chefName != null) spec = spec.and(ProductSpecifications.hasChefLike(chefName));

        return productRepository.findAll(spec, pageable);
    }

    public Page<Product> searchByNameWithinCategory(Integer categoryId, String productName, Pageable pageable) {
        Specification<Product> spec = Specification.where(ProductSpecifications.isFromCategory(categoryId))
                .and(ProductSpecifications.hasNameLike(productName));
        return productRepository.findAll(spec, pageable);
    }

    public Page<Product> searchByNameWithinChef(Integer chefId, String productName, Pageable pageable) {
        Specification<Product> spec = Specification.where(ProductSpecifications.hasChefId(chefId))
                .and(ProductSpecifications.hasNameLike(productName));
        return productRepository.findAll(spec, pageable);
    }


}
