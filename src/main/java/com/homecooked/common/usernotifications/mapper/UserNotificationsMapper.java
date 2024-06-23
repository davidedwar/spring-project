package com.homecooked.common.usernotifications.mapper;

import com.homecooked.common.mapper.BaseMapper;
import com.homecooked.common.usernotifications.dto.NotificationCreateUpdateDto;
import com.homecooked.common.usernotifications.dto.NotificationsResponseDto;
import com.homecooked.common.usernotifications.entity.UserNotifications;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserNotificationsMapper extends BaseMapper<UserNotifications, NotificationCreateUpdateDto, NotificationsResponseDto> {
}
