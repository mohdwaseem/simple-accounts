package com.simple.accounts.inventory.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class InventoryReportDTO {
    private Long productId;
    private String productCode;
    private String productName;
    private String category;
    private String brand;
    private String uom;
    private BigDecimal currentStock;
    private BigDecimal minStockLevel;
    private BigDecimal maxStockLevel;
    private String warehouse;
}
