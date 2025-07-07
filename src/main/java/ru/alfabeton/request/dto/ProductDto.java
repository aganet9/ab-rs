package ru.alfabeton.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.alfabeton.request.enums.ProductCategory;

import java.math.BigDecimal;

@Data
@Schema(description = "Данные продукта")
public class ProductDto {

    @Schema(description = "Идентификатор продукта", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Категория продукта", example = "CONCRETE_NATURAL")
    private ProductCategory category;

    @NotNull
    @DecimalMin(value = "0.0")
    @Schema(description = "Цена продукта за 1 м3", example = "5200")
    private BigDecimal priceRub;
}
