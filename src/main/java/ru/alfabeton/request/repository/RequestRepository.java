package ru.alfabeton.request.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.alfabeton.request.entity.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @EntityGraph(attributePaths = {"items", "items.product"})
    List<Request> findAll();

    @EntityGraph(attributePaths = {"items", "items.product"})
    Optional<Request> findById(Long id);
}
