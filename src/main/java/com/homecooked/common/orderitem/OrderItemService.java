package com.homecooked.common.orderitem;

import com.homecooked.common.orderitem.projection.OrderItemsListProjection;
import com.homecooked.common.orderitem.repository.OrderItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class OrderItemService {

    private final OrderItemsRepository orderItemsRepository;

    public List<OrderItemsListProjection> findByOrderId(Integer orderId) {
        return orderItemsRepository.findByOrderId(orderId);
    }

}
