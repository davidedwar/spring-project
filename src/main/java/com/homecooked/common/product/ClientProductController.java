package com.homecooked.common.product;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cl/api/product")
public class ClientProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;


    @GetMapping("/search")
    public Page<Product> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String chefName,
            Pageable pageable
    ) {
        return productService.searchProducts(name, chefName, pageable);
    }

    @GetMapping("/search/chef/{id}")
    public Page<Product> searchWithinChef(
            @PathVariable(value = "id") Integer chefId,
            @RequestParam String productName,
            Pageable pageable
    ) {
        return productService.searchByNameWithinChef(chefId, productName, pageable);
    }


    @GetMapping("/search/category/{id}")
    public Page<Product> searchWithinCategory(
            @PathVariable Integer categoryId,
            @RequestParam String productName,
            Pageable pageable
    ) {
        return productService.searchByNameWithinChef(categoryId, productName, pageable);
    }


    //TODO SORT BY PRODUCTS NEAR ME, NEED TO IMPLEMENT THE LOCATION AND THE IMAGES//

}
