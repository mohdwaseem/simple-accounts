package com.simple.accounts.inventory.repository;

import com.simple.accounts.inventory.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    boolean existsByName(String name);
}
