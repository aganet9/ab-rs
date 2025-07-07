package ru.alfabeton.request.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Данные продуктов в заявке")
public class RequestItemDto {

    @NotNull
    @Schema(description = "Идентификатор продукта", example = "1")
    private Long productId;

    @NotNull
    @DecimalMin(value = "0.0")
    @Schema(description = "Объем продукта", example = "10.2")
    private Double volumeM3;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Цена продукта за 1 м3", example = "5200", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal priceRub;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Цена продукта", example = "53040", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal itemTotalPrice;
}
