package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.CurrencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Currency and its DTO CurrencyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CurrencyMapper extends EntityMapper<CurrencyDTO, Currency> {


    @Mapping(target = "products", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    Currency toEntity(CurrencyDTO currencyDTO);

    default Currency fromId(Long id) {
        if (id == null) {
            return null;
        }
        Currency currency = new Currency();
        currency.setId(id);
        return currency;
    }
}
