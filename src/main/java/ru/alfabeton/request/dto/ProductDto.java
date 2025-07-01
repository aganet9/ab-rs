package ru.alfabeton.request.dto;

import lombok.Data;
import ru.alfabeton.request.enums.ProductCategory;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long id;

    private ProductCategory category;

    private BigDecimal priceRub;

    private Integer gradeM;

    private String slumpP;

    private String mixType;

    private Double strengthB;

    private String frostF;

    private String waterW;
}
