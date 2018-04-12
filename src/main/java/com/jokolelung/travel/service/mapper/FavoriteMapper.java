package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.FavoriteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Favorite and its DTO FavoriteDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface FavoriteMapper extends EntityMapper<FavoriteDTO, Favorite> {

    @Mapping(source = "user.id", target = "userId")
    FavoriteDTO toDto(Favorite favorite);

    @Mapping(source = "userId", target = "user")
    Favorite toEntity(FavoriteDTO favoriteDTO);

    default Favorite fromId(Long id) {
        if (id == null) {
            return null;
        }
        Favorite favorite = new Favorite();
        favorite.setId(id);
        return favorite;
    }
}
