package com.simple.accounts.inventory.service;

import com.simple.accounts.inventory.dto.StockAdjustmentDTO;
import com.simple.accounts.inventory.dto.StockAuditDTO;
import com.simple.accounts.inventory.model.StockAdjustment;
import com.simple.accounts.inventory.model.StockAudit;
import java.util.List;

public interface StockAdjustmentService {
    StockAdjustment adjustStock(StockAdjustmentDTO adjustmentDTO);
    List<StockAuditDTO> getAuditTrail(Long productId);
}
