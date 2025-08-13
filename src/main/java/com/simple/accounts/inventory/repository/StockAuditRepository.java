package com.simple.accounts.inventory.repository;

import com.simple.accounts.inventory.model.StockAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockAuditRepository extends JpaRepository<StockAudit, Long> {
    List<StockAudit> findByProductId(Long productId);
}
