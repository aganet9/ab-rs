package ru.alfabeton.request.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alfabeton.request.dto.RequestDto;
import ru.alfabeton.request.dto.RequestItemDto;
import ru.alfabeton.request.dto.RequestStatusUpdateDto;
import ru.alfabeton.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping
    public List<RequestDto> findAll(){
        return requestService.findAll();
    }

    @GetMapping("/{id}")
    public RequestDto findById(@PathVariable Long id){
        return requestService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto create(@Valid @RequestBody RequestDto requestDto){
        return requestService.create(requestDto);
    }

    @PatchMapping("/{id}/status")
    public RequestDto updateStatus(@PathVariable Long id,
                                  @Valid @RequestBody RequestStatusUpdateDto statusUpdateDto){
        return requestService.updateStatus(id, statusUpdateDto.status());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        requestService.delete(id);
    }

    @PostMapping("/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestItemDto addItem(@PathVariable("id") Long requestId,
                                  @Valid @RequestBody RequestItemDto requestItemDto){
        return requestService.addItem(requestId, requestItemDto);
    }

    @DeleteMapping("/{requestId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long requestId,
                           @PathVariable Long itemId){
        requestService.deleteItem(requestId, itemId);
    }

    @PostMapping("/empty")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createEmpty(@Valid @RequestParam Long clientId) {
        return requestService.createEmpty(clientId);
    }

    @PutMapping("/{id}")
    public RequestDto update(@PathVariable Long id,
                             @Valid @RequestBody RequestDto requestDto){
        return requestService.update(id, requestDto);
    }
}
