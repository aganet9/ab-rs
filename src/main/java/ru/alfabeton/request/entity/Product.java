package ru.alfabeton.request.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.alfabeton.request.enums.ProductCategory;

import java.math.BigDecimal;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProductCategory category;

    @Column(name = "price_rub", precision = 10, scale = 2)
    private BigDecimal priceRub;
}
