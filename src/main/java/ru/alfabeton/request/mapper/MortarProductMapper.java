package ru.alfabeton.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.alfabeton.request.dto.MortarProductDto;
import ru.alfabeton.request.entity.MortarProduct;

@Mapper(componentModel = "spring")
public interface MortarProductMapper {
    MortarProductDto toDto(MortarProduct product);

    MortarProduct toEntity(MortarProductDto dto);

    void updateFromDto(MortarProductDto dto, @MappingTarget MortarProduct product);
}
