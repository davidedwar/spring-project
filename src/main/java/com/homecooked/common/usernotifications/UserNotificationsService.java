package com.homecooked.common.usernotifications;

import com.homecooked.common.chef.Chef;
import com.homecooked.common.chef.ChefService;
import com.homecooked.common.client.Client;
import com.homecooked.common.client.ClientService;
import com.homecooked.common.usernotifications.dto.NotificationCreateUpdateDto;
import com.homecooked.common.usernotifications.entity.UserNotifications;
import com.homecooked.common.usernotifications.mapper.UserNotificationsMapper;
import com.homecooked.common.usernotifications.projection.UserNotificationsProjection;
import com.homecooked.common.usernotifications.repository.UserNotificationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserNotificationsService {

    private final UserNotificationsRepository notificationsRepository;
    private final ChefService chefService;
    private final ClientService clientService;
    private final UserNotificationsMapper notificationsMapper;

    public List<UserNotificationsProjection> getMyNotifications(String domain) {

        if (domain.toUpperCase().equals("CH")) {
            Chef chef = chefService.currentChef();
            return notificationsRepository.getMyNotifications(chef.getId(), "CHEF");
        }

        Client client = clientService.currentClient();
        return notificationsRepository.getMyNotifications(client.getId(), "CLIENT");
    }

    public void createNotification(NotificationCreateUpdateDto notificationCreateUpdateDto) {
        UserNotifications notification = notificationsMapper.dtotoEntity(notificationCreateUpdateDto);
        notificationsRepository.save(notification);
    }
}
