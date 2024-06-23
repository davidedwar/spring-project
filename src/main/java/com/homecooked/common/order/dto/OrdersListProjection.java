package com.homecooked.common.order.dto;

import com.homecooked.common.constant.OrderStatus;

import java.time.LocalDateTime;

public interface OrdersListProjection {
    String getClientName();

    LocalDateTime getOrderDate();

    OrderStatus getOrderStatus();

    String getLocation();

    String getPhoneNumber();

    String getEmail();

    Boolean getIsDelivery();

    Double getDeliveryCost();

    Integer getQuantity();

    Double getTotalPrice();

    String getNote();

    String getProductName();

    String getStatus();


}
