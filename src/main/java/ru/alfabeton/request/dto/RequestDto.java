package ru.alfabeton.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.alfabeton.request.enums.RequestStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Данные заявки")
public class RequestDto {

    @Schema(description = "Идентификатор заявки", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Идентификатор клиента", example = "1")
    private Long clientId;

    @Schema(description = "Статус заявки", example = "NEW")
    private RequestStatus status;

    @Schema(description = "Комментарий")
    private String comment;

    @Schema(description = "Дата создания заявки")
    private LocalDateTime createdAt;

    @Schema(description = "Дата обновления заявки")
    private LocalDateTime updatedAt;

    @Schema(description = "Полная цена заявки")
    private BigDecimal totalPrice;

    @Schema(description = "Список продуктов в заявке")
    private List<RequestItemDto> items;
}
