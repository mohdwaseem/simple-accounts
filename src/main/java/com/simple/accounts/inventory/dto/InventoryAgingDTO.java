package com.simple.accounts.inventory.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InventoryAgingDTO {
    private Long productId;
    private String productCode;
    private String productName;
    private String category;
    private String brand;
    private String uom;
    private BigDecimal currentStock;
    private LocalDate oldestStockDate;
    private Long daysInStock;
    private BigDecimal movementLastPeriod;
    private Integer periodDays;
}
