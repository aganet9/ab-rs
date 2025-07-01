package ru.alfabeton.request.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestItemDto {

    private Long productId;

    private Double volumeM3;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal priceRub;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal priceM3;
}
