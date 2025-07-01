package ru.alfabeton.request.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestItemDto {

    @NotNull
    private Long productId;

    @NotNull
    @DecimalMin(value = "0.0")
    private Double volumeM3;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal priceRub;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal priceM3;
}
