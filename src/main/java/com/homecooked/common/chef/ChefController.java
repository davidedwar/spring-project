package com.homecooked.common.chef;


import com.homecooked.common.chef.dto.ChefCreateUpdateDto;
import com.homecooked.common.chef.dto.ChefResponseDto;
import com.homecooked.common.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ch/api/me")
public class ChefController {

    private final ChefService chefService;
    private final ProductService productService;

    @PatchMapping
    ChefResponseDto update(ChefCreateUpdateDto chefCreateUpdateDto) {

        return chefService.update(chefCreateUpdateDto);
    }

    void delete() {

    }

    @GetMapping
    ChefResponseDto me() {
        return chefService.me();
    }
}
