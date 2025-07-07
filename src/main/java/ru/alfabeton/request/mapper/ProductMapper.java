package ru.alfabeton.request.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import ru.alfabeton.request.dto.ConcreteProductDto;
import ru.alfabeton.request.dto.MortarProductDto;
import ru.alfabeton.request.dto.ProductDto;
import ru.alfabeton.request.entity.ConcreteProduct;
import ru.alfabeton.request.entity.MortarProduct;
import ru.alfabeton.request.entity.Product;

@RequiredArgsConstructor
@Mapper(componentModel = "spring", uses = {ConcreteProductMapper.class, MortarProductMapper.class})
public abstract class ProductMapper {

    private final ConcreteProductMapper concreteMapper;
    private final MortarProductMapper mortarMapper;


    public ProductDto toDto(Product product) {
        return switch (product.getCategory()) {
            case CONCRETE_GRANITE, CONCRETE_NATURAL -> concreteMapper.toDto((ConcreteProduct) product);
            case MORTAR_MASONRY, MORTAR_SCREED -> mortarMapper.toDto((MortarProduct) product);
        };
    }

    public Product toEntity(ProductDto productDto) {
        return switch (productDto.getCategory()) {
            case CONCRETE_GRANITE, CONCRETE_NATURAL -> concreteMapper.toEntity((ConcreteProductDto) productDto);
            case MORTAR_MASONRY, MORTAR_SCREED -> mortarMapper.toEntity((MortarProductDto) productDto);
        };
    }

    public void updateFromDto(ProductDto dto, Product product) {
        if (!dto.getCategory().equals(product.getCategory())) {
            throw new IllegalArgumentException("Product category not match");
        }

        switch (dto.getCategory()) {
            case CONCRETE_GRANITE, CONCRETE_NATURAL -> {
                concreteMapper.updateFromDto((ConcreteProductDto) dto, (ConcreteProduct) product);
            }
            case MORTAR_MASONRY, MORTAR_SCREED -> {
                mortarMapper.updateFromDto((MortarProductDto) dto, (MortarProduct) product);
            }
        }
    }
}
