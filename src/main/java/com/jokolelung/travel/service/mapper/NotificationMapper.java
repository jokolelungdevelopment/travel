package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.NotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Notification and its DTO NotificationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, RequestMapper.class})
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "request.id", target = "requestId")
    NotificationDTO toDto(Notification notification);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "requestId", target = "request")
    Notification toEntity(NotificationDTO notificationDTO);

    default Notification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setId(id);
        return notification;
    }
}
