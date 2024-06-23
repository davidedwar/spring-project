package com.homecooked.common.dailyoffers.dto;

public record DailyOfferCreateUpdateDto(Double newPrice, Integer productId, Boolean status) {
    
}
