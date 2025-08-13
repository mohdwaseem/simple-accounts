package com.simple.accounts.inventory.service;

import com.simple.accounts.inventory.dto.InventoryReportDTO;
import com.simple.accounts.inventory.model.Product;
import com.simple.accounts.inventory.repository.ProductRepository;
import com.simple.accounts.inventory.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryReportService {
    private final ProductRepository productRepo;
    private final StockMovementRepository movementRepo;

    public List<InventoryReportDTO> getInventoryReport(Long warehouseId, Long categoryId, Long brandId) {
        List<Product> products = productRepo.findAll();
        List<InventoryReportDTO> report = new ArrayList<>();
        for (Product product : products) {
            if (categoryId != null && (product.getCategory() == null || !categoryId.equals(product.getCategory().getId()))) continue;
            if (brandId != null && (product.getBrand() == null || !brandId.equals(product.getBrand().getId()))) continue;
            // Stock by warehouse (if provided)
            BigDecimal stock = (warehouseId != null)
                ? movementRepo.calculateStockLevelByWarehouse(product.getId(), warehouseId)
                : movementRepo.calculateStockLevel(product.getId());
            InventoryReportDTO dto = new InventoryReportDTO();
            dto.setProductId(product.getId());
            dto.setProductCode(product.getCode());
            dto.setProductName(product.getName());
            dto.setCategory(product.getCategory() != null ? product.getCategory().getName() : null);
            dto.setBrand(product.getBrand() != null ? product.getBrand().getName() : null);
            dto.setUom(product.getUnitOfMeasure() != null ? product.getUnitOfMeasure().getAbbreviation() : null);
            dto.setCurrentStock(stock != null ? stock : BigDecimal.ZERO);
            dto.setMinStockLevel(product.getMinStockLevel() != null ? BigDecimal.valueOf(product.getMinStockLevel()) : null);
            dto.setMaxStockLevel(product.getMaxStockLevel() != null ? BigDecimal.valueOf(product.getMaxStockLevel()) : null);
            dto.setWarehouse(warehouseId != null ? String.valueOf(warehouseId) : null);
            report.add(dto);
        }
        return report;
    }
}
