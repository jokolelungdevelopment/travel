package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.MessagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Messages and its DTO MessagesDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, InboxMapper.class})
public interface MessagesMapper extends EntityMapper<MessagesDTO, Messages> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "inbox.id", target = "inboxId")
    MessagesDTO toDto(Messages messages);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "inboxId", target = "inbox")
    Messages toEntity(MessagesDTO messagesDTO);

    default Messages fromId(Long id) {
        if (id == null) {
            return null;
        }
        Messages messages = new Messages();
        messages.setId(id);
        return messages;
    }
}
