package ru.alfabeton.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.alfabeton.request.dto.ConcreteProductDto;
import ru.alfabeton.request.entity.ConcreteProduct;

@Mapper(componentModel = "spring")
public interface ConcreteProductMapper {
    ConcreteProductDto toDto(ConcreteProduct product);

    ConcreteProduct toEntity(ConcreteProductDto productDto);

    void updateFromDto(ConcreteProductDto dto, @MappingTarget ConcreteProduct product);
}
