package ru.alfabeton.request.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.alfabeton.request.enums.ProductCategory;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long id;

    @NotNull
    private ProductCategory category;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal priceRub;

    @NotNull
    @Min(value = 0)
    private Integer gradeM;

    @NotBlank
    private String slumpP;

    private String mixType;

    private Double strengthB;

    private String frostF;

    private String waterW;
}
