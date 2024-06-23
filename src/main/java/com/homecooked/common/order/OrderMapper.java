package com.homecooked.common.order;

import com.homecooked.common.cart.Cart;
import com.homecooked.common.mapper.BaseMapper;
import com.homecooked.common.order.dto.OrderCreateDto;
import com.homecooked.common.order.dto.OrderResponseDto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface OrderMapper extends BaseMapper<ProductOrder, OrderCreateDto, OrderResponseDto> {

    ProductOrder cartToOrder(Cart cart);
}
