package com.homecooked.common.product;

import com.homecooked.common.product.dto.ProductCreateDto;
import com.homecooked.common.product.dto.ProductResponseDto;
import com.homecooked.common.product.projection.ProductSummaryProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ch/api/product")
public class ChefProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping("/add")
    public void addProduct(@RequestBody ProductCreateDto productCreateDto) {
        productService.addProduct(productCreateDto);
    }

    @PatchMapping("/{id}/update")
    public ProductResponseDto updateProduct(@RequestParam Integer id, ProductCreateDto updateDto) {
        return productService.updateProduct(id, updateDto);
    }

    @GetMapping("/{id}")
    public ProductResponseDto viewProduct(@RequestParam Integer id) {
        return productService.viewProduct(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@RequestParam Integer id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/all")
    public Page<ProductSummaryProjection> allProducts(Pageable pageable) {
        return productService.getAllChefProducts(pageable);
    }

}
