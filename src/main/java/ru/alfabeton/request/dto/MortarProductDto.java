package ru.alfabeton.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Данные продукта-раствора")
public class MortarProductDto extends ProductDto {

    @NotNull
    @Min(value = 0)
    @Schema(description = "Марка", example = "100")
    private Integer mark;

    @NotBlank
    @Schema(description = "Подвижность", example = "П3-П4")
    private String mobility;
}
