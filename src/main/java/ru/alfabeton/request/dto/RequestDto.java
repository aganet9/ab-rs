package ru.alfabeton.request.dto;

import lombok.Data;
import ru.alfabeton.request.enums.RequestStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequestDto {

    private Long id;

    private Long clientId;

    private RequestStatus status;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private BigDecimal totalPrice;

    private List<RequestItemDto> items;
}
