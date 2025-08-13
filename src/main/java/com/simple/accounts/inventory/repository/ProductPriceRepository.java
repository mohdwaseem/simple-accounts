package com.simple.accounts.inventory.repository;

import com.simple.accounts.inventory.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    List<ProductPrice> findByPriceListName(String priceListName);

    List<ProductPrice> findByProductId(Long productId);

    @Query("SELECT pp FROM ProductPrice pp WHERE pp.product.id = :productId " +
            "AND (pp.validFrom IS NULL OR pp.validFrom <= CURRENT_DATE) " +
            "AND (pp.validTo IS NULL OR pp.validTo >= CURRENT_DATE)")
    List<ProductPrice> findActivePrices(Long productId);

}
