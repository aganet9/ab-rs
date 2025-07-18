package ru.alfabeton.request.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "concrete_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConcreteProduct extends Product {

    @Column(name = "grade_mark")
    private Integer gradeMark;

    @Column(name = "slump_mobility")
    private String slumpMobility;

    @Column(name = "mix_type")
    private String mixType;

    @Column(name = "compressive_strength")
    private Double compressiveStrength;

    @Column(name = "frost_resistance")
    private String frostResistance;

    @Column(name = "waterproofing")
    private String waterproofing;
}
