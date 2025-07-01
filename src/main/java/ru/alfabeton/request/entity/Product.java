package ru.alfabeton.request.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alfabeton.request.enums.ProductCategory;

import java.math.BigDecimal;


@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProductCategory category;

    @Column(name = "price_rub", precision = 10, scale = 2)
    private BigDecimal priceRub;

    @Column(name = "grade_m")
    private Integer gradeM;

    @Column(name = "slump_p")
    private String slumpP;

    @Column(name = "mix_type")
    private String mixType;

    @Column(name = "strength_b")
    private Double strengthB;

    @Column(name = "frost_f")
    private String frostF;

    @Column(name = "water_w")
    private String waterW;
}
