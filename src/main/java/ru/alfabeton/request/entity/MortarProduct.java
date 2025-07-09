package ru.alfabeton.request.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mortar_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MortarProduct extends Product {

    @Column(name = "mark")
    private Integer mark;

    @Column(name = "mobility")
    private String mobility;
}
