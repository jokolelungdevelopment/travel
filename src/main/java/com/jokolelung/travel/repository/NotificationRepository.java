package com.jokolelung.travel.repository;

import com.jokolelung.travel.domain.Notification;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Notification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select notification from Notification notification where notification.user.login = ?#{principal.username}")
    List<Notification> findByUserIsCurrentUser();

}
