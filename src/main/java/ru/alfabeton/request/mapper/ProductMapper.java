package ru.alfabeton.request.mapper;

import org.mapstruct.Mapper;
import ru.alfabeton.request.dto.ProductDto;
import ru.alfabeton.request.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
}
