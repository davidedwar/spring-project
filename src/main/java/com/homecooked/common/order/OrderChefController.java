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
@RequestMapping("/ch/api/order")
public class OrderChefController {
    private final OrderService orderService;

    @PatchMapping("/{id}")
    public void updateOrderStatus(@PathVariable Integer id, @RequestParam OrderStatus status) {
        orderService.updateStatus(id, status);
    }

    @GetMapping("")
    public Page<OrdersListProjection> byStatus(Pageable pageable, @RequestParam OrderStatus status) {
        return orderService.getOrdersByStatus(pageable, status);
    }

    @PatchMapping("{id}/cancel/")
    public OrderResponseDto cancelOrder(@PathVariable Integer id) {
        return orderService.cancelOrder(id, true);
    }
}
