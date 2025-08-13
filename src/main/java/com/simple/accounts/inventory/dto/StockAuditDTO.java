
package com.simple.accounts.inventory.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StockAuditDTO {
    private Long productId;
    private int oldQuantity;
    private int newQuantity;
    private String action;
    private String performedBy;
    private LocalDateTime performedAt;
    private Long warehouseId;
}

