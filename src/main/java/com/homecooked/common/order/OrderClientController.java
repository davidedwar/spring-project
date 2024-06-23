package com.homecooked.common.order;

import com.homecooked.common.constant.OrderStatus;
import com.homecooked.common.order.dto.OrderResponseDto;
import com.homecooked.common.order.dto.OrdersListProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cl/api/order")
public class OrderClientController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDto placeOrder() {
        return orderService.placeOrder();
    }

    @PatchMapping("{id}")
    public OrderResponseDto cancelOrder(@PathVariable Integer id) {
        return orderService.cancelOrder(id, false);
    }

    @GetMapping
    public Page<OrdersListProjection> viewMyOrders(Pageable pageable, OrderStatus status) {
        return orderService.getClientOrdersByStatus(pageable, status);
    }


}
