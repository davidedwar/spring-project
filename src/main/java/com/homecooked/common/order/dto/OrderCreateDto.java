package com.homecooked.common.order.dto;

import java.time.LocalDateTime;


public class OrderCreateDto {
    private Integer clientId;
    private LocalDateTime orderDate;
    private String status;
}
