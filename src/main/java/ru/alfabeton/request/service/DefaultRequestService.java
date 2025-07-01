package ru.alfabeton.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import ru.alfabeton.request.dto.RequestDto;
import ru.alfabeton.request.dto.RequestItemDto;
import ru.alfabeton.request.entity.Product;
import ru.alfabeton.request.entity.Request;
import ru.alfabeton.request.entity.RequestItem;
import ru.alfabeton.request.enums.RequestStatus;
import ru.alfabeton.request.mapper.RequestMapper;
import ru.alfabeton.request.repository.ProductRepository;
import ru.alfabeton.request.repository.RequestRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Validated
@RequiredArgsConstructor
public class DefaultRequestService implements RequestService {

    private final RequestRepository requestRepository;
    private final ProductRepository productRepository;
    private final RequestMapper requestMapper;

    private void fillTotal(RequestDto requestDto) {
        if (requestDto.getItems() == null) {
            requestDto.setTotalPrice(BigDecimal.ZERO);
            return;
        }
        BigDecimal total = requestDto.getItems().stream()
                .map(RequestItemDto::getPriceM3)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        requestDto.setTotalPrice(total);
    }


    @Override
    public List<RequestDto> findAll() {
        return requestRepository.findAll().stream()
                .map(requestMapper::toDto)
                .peek(this::fillTotal)
                .toList();
    }

    @Override
    public RequestDto findById(Long id) {
        RequestDto requestDto = requestRepository.findById(id)
                .map(requestMapper::toDto).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));
        fillTotal(requestDto);
        return requestDto;
    }

    @Override
    public RequestDto create(RequestDto requestDto) {
        Request request = requestMapper.toEntity(requestDto);
        request.setStatus(RequestStatus.NEW);
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());

        for (RequestItem requestItem : request.getItems()) {
            Product product = productRepository.findById(requestItem.getProduct().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));
            requestItem.setProduct(product);
            requestItem.setPriceRub(product.getPriceRub());
            requestItem.setRequest(request);
        }
        RequestDto result = requestMapper.toDto(requestRepository.save(request));
        fillTotal(result);
        return result;
    }

    @Override
    public RequestDto updateStatus(Long id, RequestStatus requestStatus) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));
        request.setStatus(requestStatus);
        request.setUpdatedAt(LocalDateTime.now());
        RequestDto result = requestMapper.toDto(requestRepository.save(request));
        fillTotal(result);
        return result;
    }

    @Override
    public void delete(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public RequestItemDto addItem(Long requestId, RequestItemDto requestItemDto) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));
        Product product = productRepository.findById(requestItemDto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));

        RequestItem requestItem = RequestItem.builder()
                .request(request)
                .product(product)
                .volumeM3(requestItemDto.getVolumeM3())
                .priceRub(product.getPriceRub())
                .build();
        request.addItem(requestItem);
        requestRepository.save(request);
        return requestMapper.toDto(requestItem);
    }

    @Override
    public void deleteItem(Long requestId, Long itemId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));

        boolean removed = request.getItems()
                .removeIf(item -> item.getId().equals(itemId));
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        requestRepository.save(request);
    }

    @Override
    public RequestDto createEmpty(Long clientId) {
        Request request = Request.builder()
                .clientId(clientId)
                .status(RequestStatus.NEW)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        RequestDto result = requestMapper.toDto(requestRepository.save(request));
        result.setTotalPrice(BigDecimal.ZERO);
        return result;
    }

    @Override
    public RequestDto update(Long id, RequestDto requestDto) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));

        request.setComment(requestDto.getComment());
        request.setUpdatedAt(LocalDateTime.now());

        if (requestDto.getItems() != null) {
            request.getItems().clear();
            for (RequestItemDto item : requestDto.getItems()) {
                Product product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));

                RequestItem requestItem = RequestItem.builder()
                        .product(product)
                        .volumeM3(item.getVolumeM3())
                        .priceRub(product.getPriceRub())
                        .request(request)
                        .build();
                request.addItem(requestItem);
            }
        }

        RequestDto result = requestMapper.toDto(requestRepository.save(request));
        fillTotal(result);
        return result;
    }
}
