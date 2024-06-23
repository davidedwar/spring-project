package com.homecooked.common.reviews.dto;

import java.time.LocalDateTime;

public record ReviewsResponseDto(Integer orderId, Double Rating, String review, LocalDateTime createdAt,
                                 String clientName) {
}
