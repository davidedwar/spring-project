package com.homecooked.common.orderitem.repository;

import com.homecooked.common.orderitem.OrderItem;
import com.homecooked.common.orderitem.projection.OrderItemsListProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItemsListProjection> findByOrderId(Integer orderId);

}
