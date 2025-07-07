package ru.alfabeton.request.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mortar_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MortarProduct extends Product {

    @Column(name = "mark")
    private Integer mark;

    @Column(name = "mobility")
    private String mobility;
}
