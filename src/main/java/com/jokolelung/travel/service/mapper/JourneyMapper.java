package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.JourneyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Journey and its DTO JourneyDTO.
 */
@Mapper(componentModel = "spring", uses = {TripMapper.class, CityMapper.class, CountryMapper.class})
public interface JourneyMapper extends EntityMapper<JourneyDTO, Journey> {

    @Mapping(source = "trip.id", target = "tripId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "country.id", target = "countryId")
    JourneyDTO toDto(Journey journey);

    @Mapping(source = "tripId", target = "trip")
    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "countryId", target = "country")
    Journey toEntity(JourneyDTO journeyDTO);

    default Journey fromId(Long id) {
        if (id == null) {
            return null;
        }
        Journey journey = new Journey();
        journey.setId(id);
        return journey;
    }
}
