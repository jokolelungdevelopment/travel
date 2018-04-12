package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.RequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Request and its DTO RequestDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductMapper.class})
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    RequestDTO toDto(Request request);

    @Mapping(target = "offers", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "productId", target = "product")
    Request toEntity(RequestDTO requestDTO);

    default Request fromId(Long id) {
        if (id == null) {
            return null;
        }
        Request request = new Request();
        request.setId(id);
        return request;
    }
}
