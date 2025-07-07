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
import ru.alfabeton.request.dto.RequestDto;
import ru.alfabeton.request.dto.RequestItemDto;
import ru.alfabeton.request.dto.RequestStatusUpdateDto;
import ru.alfabeton.request.service.RequestService;

import java.util.List;

@Tag(name = "Requests API", description = "Работа с заявками")
@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@Validated
public class RequestController {

    private final RequestService requestService;

    @Operation(summary = "Получить список заявок")
    @GetMapping
    public List<RequestDto> findAll() {
        return requestService.findAll();
    }

    @Operation(summary = "Получить заявку по идентификатору")
    @GetMapping("/{id}")
    public RequestDto findById(@PathVariable Long id) {
        return requestService.findById(id);
    }

    @Operation(summary = "Создать новую заявку")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto create(@Valid @RequestBody RequestDto requestDto) {
        return requestService.create(requestDto);
    }

    @Operation(summary = "Обновить статус существующей заявки")
    @PatchMapping("/{id}/status")
    public RequestDto updateStatus(@PathVariable Long id,
                                   @Valid @RequestBody RequestStatusUpdateDto statusUpdateDto) {
        return requestService.updateStatus(id, statusUpdateDto.status());
    }

    @Operation(summary = "Удалить заявку по идентификатору")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        requestService.delete(id);
    }

    @Operation(summary = "Добавить продукт в заявку")
    @PostMapping("/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestItemDto addItem(@PathVariable("id") Long requestId,
                                  @Valid @RequestBody RequestItemDto requestItemDto) {
        return requestService.addItem(requestId, requestItemDto);
    }

    @Operation(summary = "Удалить продукт из заявки")
    @DeleteMapping("/{requestId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long requestId,
                           @PathVariable Long itemId) {
        requestService.deleteItem(requestId, itemId);
    }

    @Operation(summary = "Создать пустую заявку")
    @PostMapping("/empty")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createEmpty(@Valid @RequestParam Long clientId) {
        return requestService.createEmpty(clientId);
    }

    @Operation(summary = "Обновить данные существующей заявки")
    @PutMapping("/{id}")
    public RequestDto update(@PathVariable Long id,
                             @Valid @RequestBody RequestDto requestDto) {
        return requestService.update(id, requestDto);
    }

    @Operation(summary = "Получить список заявок с пагинацией")
    @GetMapping("/paged")
    public Page<RequestDto> findAllPaged(@RequestParam int page, @RequestParam int size) {
        return requestService.findAllPaged(PageRequest.of(page, size));
    }
}
