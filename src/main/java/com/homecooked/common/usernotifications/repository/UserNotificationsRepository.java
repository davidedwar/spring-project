package com.homecooked.common.usernotifications.repository;

import com.homecooked.common.usernotifications.entity.UserNotifications;
import com.homecooked.common.usernotifications.projection.UserNotificationsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationsRepository extends JpaRepository<UserNotifications, Integer> {
    @Query("SELECT un.recipientId AS recipientId, un.recipientType AS recipientType, un.content AS content," +
            " un.seen AS seen FROM UserNotifications un WHERE un.recipientId = :userId AND un.recipientType = :type")
    List<UserNotificationsProjection> getMyNotifications(Integer userId, String type);


}
