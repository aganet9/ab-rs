package ru.alfabeton.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alfabeton.request.entity.RequestItem;

public interface RequestItemsRepository extends JpaRepository<RequestItem, Long> {
    boolean existsByProductId(Long productId);
}
