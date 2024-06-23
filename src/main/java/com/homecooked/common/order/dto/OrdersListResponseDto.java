package com.homecooked.common.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrdersListResponseDto(Integer id, LocalDateTime orderDate, String clientName, Integer totalPrice,
                                    Double deliveryCost, List<OrderSummaryDto> orderSummaryDtoList) {
}
