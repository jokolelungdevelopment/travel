package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.OfferDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Offer and its DTO OfferDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, RequestMapper.class, TripMapper.class})
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "request.id", target = "requestId")
    @Mapping(source = "trip.id", target = "tripId")
    OfferDTO toDto(Offer offer);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "requestId", target = "request")
    @Mapping(source = "tripId", target = "trip")
    Offer toEntity(OfferDTO offerDTO);

    default Offer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Offer offer = new Offer();
        offer.setId(id);
        return offer;
    }
}
