package com.homecooked.common.cart;

import com.homecooked.common.cart.dto.CartResponseDto;
import com.homecooked.common.cart.dto.CartUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cl/api/cart")
public class CartClientController {

    private final CartService cartService;

    @PostMapping
    public CartResponseDto addToCart(CartUpdateDto dto) {
        return cartService.addToCart(dto);
    }

    @DeleteMapping
    public CartResponseDto removeFromCart(CartUpdateDto dto) {
        return cartService.removeFromCart(dto);
    }

    @DeleteMapping("/clear")
    public CartResponseDto clearCart() {
        return cartService.clearCart();
    }

}
