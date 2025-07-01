package ru.alfabeton.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alfabeton.request.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
