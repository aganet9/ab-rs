package ru.alfabeton.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import ru.alfabeton.request.dto.ProductDto;
import ru.alfabeton.request.mapper.ProductMapper;
import ru.alfabeton.request.repository.ProductRepository;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class DefaultProductService  implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        var entity = productMapper.toEntity(productDto);
        entity.setId(id);
        return productMapper.toDto(productRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
