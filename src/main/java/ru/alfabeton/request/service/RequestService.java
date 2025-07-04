package ru.alfabeton.request.service;

import ru.alfabeton.request.dto.RequestDto;
import ru.alfabeton.request.dto.RequestItemDto;
import ru.alfabeton.request.enums.RequestStatus;

import java.util.List;

public interface RequestService {
    List<RequestDto> findAll();

    RequestDto findById(Long id);

    RequestDto create(RequestDto requestDto);

    RequestDto updateStatus(Long id, RequestStatus requestStatus);

    void delete(Long id);

    RequestItemDto addItem(Long requestId, RequestItemDto requestItemDto);

    void deleteItem(Long requestId, Long itemId);

    RequestDto createEmpty (Long clientId);

    RequestDto update(Long id, RequestDto requestDto);
}
