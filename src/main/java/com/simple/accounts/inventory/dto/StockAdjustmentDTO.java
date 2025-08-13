
package com.simple.accounts.inventory.dto;

import lombok.Data;

@Data
public class StockAdjustmentDTO {
    private Long productId;
    private int quantity;
    private String reason;
    private String adjustedBy;
    private Long warehouseId;
}

