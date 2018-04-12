package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.TripDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Trip and its DTO TripDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TripMapper extends EntityMapper<TripDTO, Trip> {

    @Mapping(source = "user.id", target = "userId")
    TripDTO toDto(Trip trip);

    @Mapping(target = "journeys", ignore = true)
    @Mapping(target = "offers", ignore = true)
    @Mapping(target = "preOrders", ignore = true)
    @Mapping(source = "userId", target = "user")
    Trip toEntity(TripDTO tripDTO);

    default Trip fromId(Long id) {
        if (id == null) {
            return null;
        }
        Trip trip = new Trip();
        trip.setId(id);
        return trip;
    }
}
