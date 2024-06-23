package com.homecooked.common.cart.dto;

import java.math.BigDecimal;

public record CartItemDto(Integer productId, String productName, Integer quantity, BigDecimal totalPrice) {
}
