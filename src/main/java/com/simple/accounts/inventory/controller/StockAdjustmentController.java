package com.simple.accounts.inventory.controller;

import com.simple.accounts.inventory.dto.StockAdjustmentDTO;
import com.simple.accounts.inventory.dto.StockAuditDTO;
import com.simple.accounts.inventory.model.StockAdjustment;
import com.simple.accounts.inventory.service.StockAdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory/adjustment")
public class StockAdjustmentController {
    @Autowired
    private StockAdjustmentService stockAdjustmentService;

    @PostMapping
    public StockAdjustment adjustStock(@RequestBody StockAdjustmentDTO adjustmentDTO) {
        // Now expects warehouseId in the DTO
        return stockAdjustmentService.adjustStock(adjustmentDTO);
    }

    @GetMapping("/audit/{productId}")
    public List<StockAuditDTO> getAuditTrail(@PathVariable Long productId,
                                             @RequestParam(required = false) Long warehouseId) {
        // Optionally filter audit trail by warehouse
        List<StockAuditDTO> audits = stockAdjustmentService.getAuditTrail(productId);
        if (warehouseId != null) {
            audits = audits.stream()
                .filter(a -> warehouseId.equals(a.getWarehouseId()))
                .toList();
        }
        return audits;
    }
}
