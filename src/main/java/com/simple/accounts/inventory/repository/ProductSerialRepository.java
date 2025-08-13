package com.simple.accounts.inventory.repository;

import com.simple.accounts.inventory.model.ProductSerial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSerialRepository extends JpaRepository<ProductSerial, Long> {
    Optional<ProductSerial> findBySerialNumberAndProductId(String serialNumber, Long productId);
    List<ProductSerial> findByProductId(Long productId);
    List<ProductSerial> findByStockMovementId(Long stockMovementId);
}
