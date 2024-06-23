package com.homecooked.common.cart.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartResponseDto {
    List<CartItemDto> cartItems;
    BigDecimal totalPrice;
}
