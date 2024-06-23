package com.homecooked.common.order;

import com.homecooked.common.auth.AuthService;
import com.homecooked.common.cart.Cart;
import com.homecooked.common.cart.CartRepository;
import com.homecooked.common.client.Client;
import com.homecooked.common.client.ClientService;
import com.homecooked.common.constant.OrderStatus;
import com.homecooked.common.order.dto.OrderResponseDto;
import com.homecooked.common.order.dto.OrdersListProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final ClientService clientService;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public void updateStatus(Integer id, OrderStatus status) {
        ProductOrder productOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        productOrder.setStatus(status);
        orderRepository.save(productOrder);
    }

    @Transactional
    public Page<OrdersListProjection> getOrdersByStatus(Pageable pageable, OrderStatus status) {
        Integer chefId = authService.getCurrentChef().getId();
        return orderRepository.findOrdersByStatus(pageable, status, chefId);
    }

    @Transactional
    public OrderResponseDto cancelOrder(Integer id, boolean isChef) {
        //TODO : IMPLEMENT MONEY REFUND
        ProductOrder productOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        productOrder.setStatus(isChef ? OrderStatus.CANCELLED_BY_CHEF : OrderStatus.CANCELLED_BY_CLIENT);
        return orderMapper.toResponseDto(productOrder);
    }

    @Transactional
    public OrderResponseDto placeOrder() {
        Client client = clientService.currentClient();

        Cart currentCart = cartRepository.findByClientId(client.getId()).orElseThrow(() -> new EntityNotFoundException("" +
                "No cart found for current user"));
        ProductOrder order = orderMapper.cartToOrder(currentCart);

        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        return orderMapper.toResponseDto(order);
    }

    @Transactional
    public Page<OrdersListProjection> getClientOrdersByStatus(Pageable pageable, OrderStatus status) {
        Client client = clientService.currentClient();
        return orderRepository.findClientOrdersByStatus(pageable, status, client.getId());
    }


}
