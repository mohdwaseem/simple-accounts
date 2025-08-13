package com.simple.accounts.inventory.repository;

import com.simple.accounts.inventory.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProductId(Long productId);

    List<StockMovement> findByType(String type);

    List<StockMovement> findByProductIdAndType(Long productId, String type);

    @Query("SELECT COALESCE(SUM(m.quantity), 0) FROM StockMovement m WHERE m.product.id = :productId")
    BigDecimal calculateStockLevel(@Param("productId") Long productId);
}