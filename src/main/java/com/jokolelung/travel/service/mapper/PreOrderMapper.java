package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.PreOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PreOrder and its DTO PreOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductMapper.class, TripMapper.class})
public interface PreOrderMapper extends EntityMapper<PreOrderDTO, PreOrder> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "trip.id", target = "tripId")
    PreOrderDTO toDto(PreOrder preOrder);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "tripId", target = "trip")
    PreOrder toEntity(PreOrderDTO preOrderDTO);

    default PreOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        PreOrder preOrder = new PreOrder();
        preOrder.setId(id);
        return preOrder;
    }
}
