package com.homecooked.common.mapper;

import org.mapstruct.MappingTarget;

public interface BaseMapper<E, C, R> {

    R toResponseDto(E entity);

    E dtotoEntity(C createDto);

    void updateEntityFromDto(C dto, @MappingTarget E entity);
}

