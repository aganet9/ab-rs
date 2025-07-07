package ru.alfabeton.request.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alfabeton.request.dto.ProductDto;
import ru.alfabeton.request.service.ProductService;

import java.util.List;

@Tag(name = "Products API", description = "Работа с продуктами")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @Operation(description = "Получить список продуктов")
    @GetMapping
    public List<ProductDto> findAll() {
        return productService.findAll();
    }

    @Operation(description = "Получить список продуктов с пагинацией")
    @GetMapping("/paged")
    public Page<ProductDto> findAllPaged(@RequestParam int page, @RequestParam int size) {
        return productService.findAllPaged(PageRequest.of(page, size));
    }

    @Operation(description = "Получить продукт по идентификатору")
    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @Operation(description = "Создать новый продукт")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@Valid @RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    @Operation(description = "Обновить существующий продукт")
    @PutMapping("/{id}")
    public ProductDto update(@PathVariable Long id,
                             @Valid @RequestBody ProductDto productDto) {
        return productService.update(id, productDto);
    }

    @Operation(description = "Удалить существующий продукт")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
