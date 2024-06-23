package com.homecooked.common.product.mapper;

import com.homecooked.common.mapper.BaseMapper;
import com.homecooked.common.product.Product;
import com.homecooked.common.product.dto.ProductCreateDto;
import com.homecooked.common.product.dto.ProductResponseDto;
import com.homecooked.common.product.dto.ProductSummaryDto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ProductMapper extends BaseMapper<Product, ProductCreateDto, ProductResponseDto> {
    ProductSummaryDto entityToProductSummaryDto(Product product);
}
