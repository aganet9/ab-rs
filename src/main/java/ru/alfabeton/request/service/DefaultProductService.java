package ru.alfabeton.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.alfabeton.request.dto.ConcreteProductDto;
import ru.alfabeton.request.dto.MortarProductDto;
import ru.alfabeton.request.dto.ProductDto;
import ru.alfabeton.request.entity.ConcreteProduct;
import ru.alfabeton.request.entity.MortarProduct;
import ru.alfabeton.request.entity.Product;
import ru.alfabeton.request.exception.ProductCategoryNotMatchException;
import ru.alfabeton.request.exception.ProductInUseException;
import ru.alfabeton.request.exception.ProductNotFoundException;
import ru.alfabeton.request.mapper.ConcreteProductMapper;
import ru.alfabeton.request.mapper.MortarProductMapper;
import ru.alfabeton.request.repository.ProductRepository;
import ru.alfabeton.request.repository.RequestItemsRepository;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;
    private final RequestItemsRepository requestItemsRepository;
    private final MortarProductMapper mortarMapper;
    private final ConcreteProductMapper concreteMapper;

    private ProductDto toDto(Product product) {
        return switch (product.getCategory()){
            case CONCRETE_GRANITE, CONCRETE_NATURAL -> concreteMapper.toDto((ConcreteProduct) product);
            case MORTAR_MASONRY, MORTAR_SCREED -> mortarMapper.toDto((MortarProduct) product);
        };
    }

    private Product toEntity(ProductDto productDto) {
        return switch (productDto.getCategory()){
            case CONCRETE_GRANITE, CONCRETE_NATURAL -> concreteMapper.toEntity((ConcreteProductDto) productDto);
            case MORTAR_MASONRY, MORTAR_SCREED -> mortarMapper.toEntity((MortarProductDto) productDto);
        };
    }

    private void updateFromDto(ProductDto dto, Product product) {
        if (!dto.getCategory().equals(product.getCategory())) {
            throw new ProductCategoryNotMatchException("Product category not match");
        }

        switch (dto.getCategory()) {
            case CONCRETE_GRANITE, CONCRETE_NATURAL -> concreteMapper.updateFromDto((ConcreteProductDto) dto, (ConcreteProduct) product);
            case MORTAR_MASONRY, MORTAR_SCREED -> mortarMapper.updateFromDto((MortarProductDto) dto, (MortarProduct) product);
        }
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        return this.toDto(productRepository.save(this.toEntity(productDto)));
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        var existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        this.updateFromDto(productDto, existing);
        return this.toDto(productRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        if (requestItemsRepository.existsByProductId(id)) {
            throw new ProductInUseException(id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDto> findAllPaged(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::toDto);
    }
}
