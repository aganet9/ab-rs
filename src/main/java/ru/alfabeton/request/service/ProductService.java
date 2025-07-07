package ru.alfabeton.request.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.alfabeton.request.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();

    ProductDto findById(Long id);

    ProductDto create(ProductDto productDto);

    ProductDto update(Long id, ProductDto productDto);

    void delete(Long id);

    Page<ProductDto> findAllPaged(Pageable pageable);
}
