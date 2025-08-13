package com.simple.accounts.inventory.repository;

import com.simple.accounts.inventory.model.StockAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment, Long> {
    List<StockAdjustment> findByProductId(Long productId);
}
