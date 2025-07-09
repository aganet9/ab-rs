package ru.alfabeton.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.alfabeton.request.dto.MortarProductDto;
import ru.alfabeton.request.entity.MortarProduct;

@Mapper(componentModel = "spring")
public interface MortarProductMapper {
    @Mapping(target = "category", source = "category")
    @Mapping(target = "priceRub", source = "priceRub")
    MortarProductDto toDto(MortarProduct product);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "priceRub", source = "priceRub")
    MortarProduct toEntity(MortarProductDto dto);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "priceRub", source = "priceRub")
    @Mapping(target = "id", ignore = true)
    void updateFromDto(MortarProductDto dto, @MappingTarget MortarProduct product);
}
