package com.homecooked.common.cart;

import com.homecooked.common.mapper.BaseMapper;
import com.homecooked.common.order.ProductOrder;
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
public interface CartMapper extends BaseMapper<ProductOrder, OrderCreateDto, OrderResponseDto> {
}
