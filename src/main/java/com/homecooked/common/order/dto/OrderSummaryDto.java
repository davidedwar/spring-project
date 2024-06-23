package com.homecooked.common.order.dto;

public record OrderSummaryDto(String productName, Integer quantity, Double price) {
}
