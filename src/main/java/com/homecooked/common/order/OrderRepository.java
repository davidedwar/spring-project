package com.homecooked.common.order;

import com.homecooked.common.constant.OrderStatus;
import com.homecooked.common.order.dto.OrdersListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<ProductOrder, Integer> {

    @Query("SELECT o.client.fullName AS clientName, o.client.email AS email, o.client.phoneNumber as phoneNumber, " +
            "o.orderDate AS orderDate, o.status AS status, o.totalPrice AS totalPrice, " +
            "o.note AS note, o.location AS location, o.isDelivery as isDelivery, o.deliveryCost as deliveryCost, " +
            "oi.productName AS productName, oi.quantity AS quantity " +
            "FROM ProductOrder o JOIN o.orderItems oi JOIN o.client cl " +
            "WHERE o.status = :status AND o.chef.id = :chefId")
    Page<OrdersListProjection> findOrdersByStatus(Pageable pageable, OrderStatus status, Integer chefId);

    @Query("SELECT o.client.fullName AS clientName, o.client.email AS email, o.client.phoneNumber as phoneNumber, " +
            "o.orderDate AS orderDate, o.status AS status, o.totalPrice AS totalPrice, " +
            "o.note AS note, o.location AS location, o.isDelivery as isDelivery, o.deliveryCost as deliveryCost, " +
            "oi.productName AS productName, oi.quantity AS quantity " +
            "FROM ProductOrder o JOIN o.orderItems oi JOIN o.client cl " +
            "WHERE o.status = :status AND o.client.id = :clientId")
    Page<OrdersListProjection> findClientOrdersByStatus(Pageable pageable, OrderStatus status, Integer clientId);

}
