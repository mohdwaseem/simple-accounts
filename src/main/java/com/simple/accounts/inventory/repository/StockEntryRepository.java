package com.simple.accounts.inventory.repository;

import com.simple.accounts.inventory.model.StockEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {
    List<StockEntry> findByProductIdOrderByEntryDateAsc(Long productId);
    List<StockEntry> findByProductIdOrderByEntryDateDesc(Long productId);
}
