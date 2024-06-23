package com.homecooked.common.chef.mapper;

import com.homecooked.common.chef.Chef;
import com.homecooked.common.chef.dto.ChefCreateUpdateDto;
import com.homecooked.common.chef.dto.ChefResponseDto;
import com.homecooked.common.user.dto.UserResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ChefMapper {
    ChefCreateUpdateDto toCreateUpdateDto(Chef entity);

    ChefResponseDto toResponseDto(Chef entity);

    UserResponseDto toUserResponseDto(Chef entity);

    Chef userResponseDtoToEntity(UserResponseDto userResponseDto);

    Chef createUpdateDtoToEntity(ChefCreateUpdateDto createUpdateDto);

    Chef responseDtoToEntity(ChefResponseDto responseDto);

    void updateEntityFromDto(ChefCreateUpdateDto dto, @MappingTarget Chef entity);
}
