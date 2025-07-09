package ru.alfabeton.request.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "request_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "volume_m3")
    private Double volumeM3;

    @Column(name = "price_rub", precision = 10, scale = 2)
    private BigDecimal priceRub;
}
