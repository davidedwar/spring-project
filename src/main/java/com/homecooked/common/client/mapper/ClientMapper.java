package com.homecooked.common.client.mapper;

import com.homecooked.common.client.Client;
import com.homecooked.common.client.dto.ClientCreateUpdateDto;
import com.homecooked.common.client.dto.ClientResponseDto;
import com.homecooked.common.user.dto.UserResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ClientMapper {
    ClientCreateUpdateDto toCreateUpdateDto(Client entity);

    ClientResponseDto toResponseDto(Client entity);

    UserResponseDto toUserResponseDto(Client entity);

    Client userResponseDtoToEntity(UserResponseDto userResponseDto);

    Client createUpdateDtoToEntity(ClientCreateUpdateDto createUpdateDto);

    Client responseDtoToEntity(ClientResponseDto responseDto);

    void updateEntityFromDto(ClientCreateUpdateDto dto, @MappingTarget Client entity);
}
