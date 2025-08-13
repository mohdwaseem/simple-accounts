package com.simple.accounts.inventory.repository;

import com.simple.accounts.inventory.model.UomConversion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UomConversionRepository extends JpaRepository<UomConversion, Long> {
    List<UomConversion> findByProductId(Long productId);
    Optional<UomConversion> findByProductIdAndFromUomIdAndToUomId(Long productId, Long fromUomId, Long toUomId);
}
