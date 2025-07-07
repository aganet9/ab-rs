package ru.alfabeton.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.alfabeton.request.enums.RequestStatus;

@Schema(description = "Обновление статуса заявки")
public record RequestStatusUpdateDto(
        @Schema(description = "Новый статус заявки", example = "NEW") RequestStatus status) {
}
