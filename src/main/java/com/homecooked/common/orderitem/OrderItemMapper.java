package com.homecooked.common.orderitem;

import com.homecooked.common.cart.dto.CartItemDto;
import com.homecooked.common.mapper.BaseMapper;
import com.homecooked.common.order.dto.OrderResponseDto;
import com.homecooked.common.orderitem.dto.OrderItemCreateDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface OrderItemMapper extends BaseMapper<OrderItem, OrderItemCreateDto, OrderResponseDto> {


    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    CartItemDto toCartItemDto(OrderItem orderItem);
}