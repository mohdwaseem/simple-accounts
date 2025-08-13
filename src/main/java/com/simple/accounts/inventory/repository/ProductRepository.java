package com.simple.accounts.inventory.repository;

import com.simple.accounts.inventory.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = { "prices" })
    Optional<Product> findWithPricesById(Long id);

    boolean existsByCode(String code);
}
