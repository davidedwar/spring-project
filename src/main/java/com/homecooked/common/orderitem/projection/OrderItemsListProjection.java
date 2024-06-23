package com.homecooked.common.orderitem.projection;

public interface OrderItemsListProjection {
    Integer getQuantity();

    Double getTotalPrice();

    String getProductName();
}
