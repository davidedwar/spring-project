package com.homecooked.common.usernotifications.dto;

public record NotificationsResponseDto(Integer id, Integer recipientId, String recipientType, String content,
                                       boolean seen) {
}
