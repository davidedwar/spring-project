package com.homecooked.common.usernotifications.controller;

import com.homecooked.common.usernotifications.UserNotificationsService;
import com.homecooked.common.usernotifications.projection.UserNotificationsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserNotificationsController {

    private final UserNotificationsService notificationsService;

    @GetMapping("/{domain}/api/notifications")
    public List<UserNotificationsProjection> myNotifications(@PathVariable String domain) {
        return notificationsService.getMyNotifications(domain);
    }
}
