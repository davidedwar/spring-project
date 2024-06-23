package com.homecooked.common.dailyoffers.mapper;

import com.homecooked.common.dailyoffers.dto.DailyOfferCreateUpdateDto;
import com.homecooked.common.dailyoffers.dto.DailyOffersResponseDto;
import com.homecooked.common.dailyoffers.entity.DailyOffers;
import com.homecooked.common.mapper.BaseMapper;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface DailyOffersMapper extends BaseMapper<DailyOffers, DailyOfferCreateUpdateDto, DailyOffersResponseDto> {

    List<DailyOffersResponseDto> ListEntityToResponse(List<DailyOffers> dailyOffers);
}
