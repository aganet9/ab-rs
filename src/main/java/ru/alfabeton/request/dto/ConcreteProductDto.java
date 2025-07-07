package ru.alfabeton.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Данные продукта-бетона")
public class ConcreteProductDto extends ProductDto {

    @NotNull
    @Min(value = 0)
    @Schema(description = "Марка", example = "100")
    private Integer gradeMark;

    @NotBlank
    @Schema(description = "Подвижность", example = "П3-П4")
    private String slumpMobility;

    @Schema(description = "Тип смеси", example = "БСТ")
    private String mixType;

    @Schema(description = "Прочность", example = "7.5")
    private Double compressiveStrength;

    @Schema(description = "Морозостойкость", example = "25-75")
    private String frostResistance;

    @Schema(description = "Водонепроницаемость", example = "2-4")
    private String waterproofing;
}
