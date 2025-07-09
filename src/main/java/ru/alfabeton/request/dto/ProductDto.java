package ru.alfabeton.request.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.alfabeton.request.entity.MortarProduct;
import ru.alfabeton.request.enums.ProductCategory;

import java.math.BigDecimal;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "category",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConcreteProductDto.class, name = "CONCRETE_NATURAL"),
        @JsonSubTypes.Type(value = ConcreteProductDto.class, name = "CONCRETE_GRANITE"),
        @JsonSubTypes.Type(value = MortarProductDto.class, name = "MORTAR_MASONRY"),
        @JsonSubTypes.Type(value = MortarProductDto.class, name = "MORTAR_SCREED")
})
@Data
@Schema(description = "Данные продукта", oneOf = {ConcreteProductDto.class, MortarProductDto.class})
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
