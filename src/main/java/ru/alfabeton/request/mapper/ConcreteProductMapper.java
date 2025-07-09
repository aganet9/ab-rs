package ru.alfabeton.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.alfabeton.request.dto.ConcreteProductDto;
import ru.alfabeton.request.entity.ConcreteProduct;

@Mapper(componentModel = "spring")
public interface ConcreteProductMapper {
    @Mapping(target = "category", source = "category")
    @Mapping(target = "priceRub", source = "priceRub")
    ConcreteProductDto toDto(ConcreteProduct product);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "priceRub", source = "priceRub")
    ConcreteProduct toEntity(ConcreteProductDto productDto);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "priceRub", source = "priceRub")
    @Mapping(target = "id", ignore = true)
    void updateFromDto(ConcreteProductDto dto, @MappingTarget ConcreteProduct product);
}
