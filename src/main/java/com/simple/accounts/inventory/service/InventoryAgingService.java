package com.simple.accounts.inventory.service;

import com.simple.accounts.inventory.dto.InventoryAgingDTO;
import com.simple.accounts.inventory.model.Product;
import com.simple.accounts.inventory.model.StockMovement;
import com.simple.accounts.inventory.repository.ProductRepository;
import com.simple.accounts.inventory.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryAgingService {
    private final ProductRepository productRepo;
    private final StockMovementRepository movementRepo;

    public List<InventoryAgingDTO> getAgingReport(Integer periodDays) {
        if (periodDays == null || periodDays <= 0) periodDays = 90;
        List<Product> products = productRepo.findAll();
        List<InventoryAgingDTO> report = new ArrayList<>();
        LocalDateTime periodStart = LocalDateTime.now().minusDays(periodDays);
        for (Product product : products) {
            List<StockMovement> movements = movementRepo.findByProductId(product.getId());
            LocalDate oldest = movements.stream()
                .map(m -> m.getTimestamp().toLocalDate())
                .min(LocalDate::compareTo)
                .orElse(null);
            BigDecimal currentStock = movementRepo.calculateStockLevel(product.getId());
            // Movement in last period
            BigDecimal moved = movements.stream()
                .filter(m -> m.getTimestamp().isAfter(periodStart))
                .map(StockMovement::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            InventoryAgingDTO dto = new InventoryAgingDTO();
            dto.setProductId(product.getId());
            dto.setProductCode(product.getCode());
            dto.setProductName(product.getName());
            dto.setCategory(product.getCategory() != null ? product.getCategory().getName() : null);
            dto.setBrand(product.getBrand() != null ? product.getBrand().getName() : null);
            dto.setUom(product.getUnitOfMeasure() != null ? product.getUnitOfMeasure().getAbbreviation() : null);
            dto.setCurrentStock(currentStock != null ? currentStock : BigDecimal.ZERO);
            dto.setOldestStockDate(oldest);
            dto.setDaysInStock(oldest != null ? ChronoUnit.DAYS.between(oldest, LocalDate.now()) : null);
            dto.setMovementLastPeriod(moved);
            dto.setPeriodDays(periodDays);
            report.add(dto);
        }
        return report;
    }
}
