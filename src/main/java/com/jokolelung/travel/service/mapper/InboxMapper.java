package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.InboxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Inbox and its DTO InboxDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface InboxMapper extends EntityMapper<InboxDTO, Inbox> {

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "receiver.id", target = "receiverId")
    InboxDTO toDto(Inbox inbox);

    @Mapping(target = "messages", ignore = true)
    @Mapping(source = "senderId", target = "sender")
    @Mapping(source = "receiverId", target = "receiver")
    Inbox toEntity(InboxDTO inboxDTO);

    default Inbox fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inbox inbox = new Inbox();
        inbox.setId(id);
        return inbox;
    }
}
