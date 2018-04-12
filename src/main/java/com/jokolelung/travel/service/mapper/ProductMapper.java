package com.jokolelung.travel.service.mapper;

import com.jokolelung.travel.domain.*;
import com.jokolelung.travel.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class, CityMapper.class, CountryMapper.class, CurrencyMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "currency.id", target = "currencyId")
    ProductDTO toDto(Product product);

    @Mapping(target = "productImages", ignore = true)
    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "preOrders", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "countryId", target = "country")
    @Mapping(source = "currencyId", target = "currency")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
