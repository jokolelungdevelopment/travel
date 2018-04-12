package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.DiscussionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Discussion and its DTO DiscussionDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface DiscussionMapper extends EntityMapper<DiscussionDTO, Discussion> {

    @Mapping(source = "user.id", target = "userId")
    DiscussionDTO toDto(Discussion discussion);

    @Mapping(source = "userId", target = "user")
    Discussion toEntity(DiscussionDTO discussionDTO);

    default Discussion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Discussion discussion = new Discussion();
        discussion.setId(id);
        return discussion;
    }
}
