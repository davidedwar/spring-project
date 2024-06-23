package com.homecooked.common.product.dto;

public record ProductResponseDto(Integer id, Integer chefId, String name, String description, String categoryName,
                                 Integer minimumQuantity, Integer noticePeriod, Float price, Integer depositPercentage,
                                 String ingredients) {
}
