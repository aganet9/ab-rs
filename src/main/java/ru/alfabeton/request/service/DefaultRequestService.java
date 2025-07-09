package ru.alfabeton.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import ru.alfabeton.request.dto.RequestDto;
import ru.alfabeton.request.dto.RequestItemDto;
import ru.alfabeton.request.dto.RequestUpdateDto;
import ru.alfabeton.request.entity.Product;
import ru.alfabeton.request.entity.Request;
import ru.alfabeton.request.entity.RequestItem;
import ru.alfabeton.request.enums.RequestStatus;
import ru.alfabeton.request.exception.ProductNotFoundException;
import ru.alfabeton.request.exception.RequestNotFoundException;
import ru.alfabeton.request.mapper.RequestMapper;
import ru.alfabeton.request.repository.ProductRepository;
import ru.alfabeton.request.repository.RequestItemsRepository;
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
    private final RequestItemsRepository requestItemsRepository;
    private final RequestMapper requestMapper;

    private void fillTotal(RequestDto requestDto) {
        if (requestDto.getItems() == null) {
            requestDto.setTotalPrice(BigDecimal.ZERO);
            return;
        }
        BigDecimal total = requestDto.getItems().stream()
                .map(RequestItemDto::getItemTotalPrice)
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
                .map(requestMapper::toDto).orElseThrow(() -> new RequestNotFoundException(id));
        fillTotal(requestDto);
        return requestDto;
    }

    @Override
    public RequestDto create(RequestDto requestDto) {
        Request request = new Request();
        request.setClientId(requestDto.getClientId());
        request.setComment(requestDto.getComment());
        request.setStatus(RequestStatus.NEW);
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());

        var requestId = request.getId();

        if (requestDto.getItems() != null) {
            for (RequestItemDto itemDto : requestDto.getItems()) {
                Product product = productRepository.findById(itemDto.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException(itemDto.getProductId()));

                RequestItem item = new RequestItem();
                item.setRequest(request);
                item.setProduct(product);
                item.setPriceRub(product.getPriceRub());
                item.setVolumeM3(itemDto.getVolumeM3());

                request.addItem(item);
            }
        }

        request = requestRepository.save(request);

        RequestDto result = requestMapper.toDto(requestRepository.findById(request.getId())
                .orElseThrow(() -> new RequestNotFoundException(requestId)));

        fillTotal(result);
        return result;
    }

    @Override
    public RequestDto updateStatus(Long id, RequestStatus requestStatus) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException(id));
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
                .orElseThrow(() -> new RequestNotFoundException(requestId));
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
    public void deleteItem(Long requestId, Long productId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(requestId));

        boolean removed = request.getItems().removeIf(item ->
                item.getProduct() != null && item.getProduct().getId().equals(productId));
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found by productId: " + productId);
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
    public RequestDto update(Long id, RequestUpdateDto dto) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException(id));

        request.setComment(dto.getComment());
        request.setUpdatedAt(LocalDateTime.now());

        if (dto.getItems() != null) {
            request.getItems().clear();
            for (RequestItemDto item : dto.getItems()) {
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

    @Override
    public Page<RequestDto> findAllPaged(Pageable pageable) {
        Page<Request> page =  requestRepository.findAll(pageable);

        return page.map(request -> {
            RequestDto dto = requestMapper.toDto(request);
            fillTotal(dto);
            return dto;
        });
    }
}
