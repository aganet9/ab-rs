package ru.alfabeton.request.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.alfabeton.request.enums.ProductCategory;

import java.math.BigDecimal;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
