package com.homecooked.common.usernotifications.dto;

public record NotificationCreateUpdateDto(Integer id, Integer recipientId, String text,
                                          boolean seen) {

}
