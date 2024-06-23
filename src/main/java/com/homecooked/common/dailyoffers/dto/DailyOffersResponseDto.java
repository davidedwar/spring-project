package com.homecooked.common.dailyoffers.dto;

import com.homecooked.common.product.dto.ProductResponseDto;

public record DailyOffersResponseDto(Double newPrice, ProductResponseDto product, Boolean status) {
}
