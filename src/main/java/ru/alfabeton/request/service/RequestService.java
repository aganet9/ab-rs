package ru.alfabeton.request.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.alfabeton.request.dto.RequestDto;
import ru.alfabeton.request.dto.RequestItemDto;
import ru.alfabeton.request.dto.RequestUpdateDto;
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

    RequestDto update(Long id, RequestUpdateDto dto);

    Page<RequestDto> findAllPaged(Pageable pageable);
}
