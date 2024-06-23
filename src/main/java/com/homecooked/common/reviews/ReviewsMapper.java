package com.homecooked.common.reviews;

import com.homecooked.common.mapper.BaseMapper;
import com.homecooked.common.reviews.dto.ReviewsCreateUpdateDto;
import com.homecooked.common.reviews.dto.ReviewsResponseDto;
import com.homecooked.common.reviews.entity.Reviews;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ReviewsMapper extends BaseMapper<Reviews, ReviewsCreateUpdateDto, ReviewsResponseDto> {
}
