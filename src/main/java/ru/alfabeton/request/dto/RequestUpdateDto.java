package ru.alfabeton.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Данные для обновления заявки")
public class RequestUpdateDto {

    @Schema(description = "Комментарий")
    private String comment;

    @Schema(description = "Список продуктов в заявке")
    private List<RequestItemDto> items;
}
