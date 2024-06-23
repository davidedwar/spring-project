package com.homecooked.common.usernotifications.projection;

public interface UserNotificationsProjection {
    Integer getRecipientId();

    String getRecipientType();

    String getContent();

    boolean getSeen();


}
