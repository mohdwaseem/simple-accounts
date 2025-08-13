package com.simple.accounts.inventory.service;

import org.springframework.stereotype.Service;
import com.simple.accounts.inventory.dto.StockAdjustmentDTO;
import com.simple.accounts.inventory.dto.StockAuditDTO;
import com.simple.accounts.inventory.model.StockAdjustment;
import com.simple.accounts.inventory.model.StockAudit;
import com.simple.accounts.inventory.repository.StockAdjustmentRepository;
import com.simple.accounts.inventory.repository.StockAuditRepository;
import com.simple.accounts.inventory.repository.WarehouseRepository;
import com.simple.accounts.inventory.model.Warehouse;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockAdjustmentServiceImpl implements StockAdjustmentService {
    private final StockAdjustmentRepository adjustmentRepository;
    private final StockAuditRepository auditRepository;
    private final WarehouseRepository warehouseRepository;

    public StockAdjustmentServiceImpl(StockAdjustmentRepository adjustmentRepository, StockAuditRepository auditRepository, WarehouseRepository warehouseRepository) {
        this.adjustmentRepository = adjustmentRepository;
        this.auditRepository = auditRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public StockAdjustment adjustStock(StockAdjustmentDTO adjustmentDTO) {
        // Map DTO to entity
        StockAdjustment adjustment = new StockAdjustment();
        adjustment.setProductId(adjustmentDTO.getProductId());
        adjustment.setQuantity(adjustmentDTO.getQuantity());
        adjustment.setReason(adjustmentDTO.getReason());
        adjustment.setAdjustedBy(adjustmentDTO.getAdjustedBy());
        adjustment.setAdjustedAt(java.time.LocalDateTime.now());
        Warehouse warehouse = warehouseRepository.findById(adjustmentDTO.getWarehouseId())
            .orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));
        adjustment.setWarehouse(warehouse);
        // Save adjustment
        StockAdjustment saved = adjustmentRepository.save(adjustment);
        // Create audit
        StockAudit audit = new StockAudit();
        audit.setProductId(adjustmentDTO.getProductId());
        audit.setWarehouseId(adjustmentDTO.getWarehouseId());
        audit.setOldQuantity(0); // Should fetch from inventory
        audit.setNewQuantity(adjustmentDTO.getQuantity());
        audit.setAction(adjustmentDTO.getReason());
        audit.setPerformedBy(adjustmentDTO.getAdjustedBy());
        audit.setPerformedAt(java.time.LocalDateTime.now());
        auditRepository.save(audit);
        return saved;
    }

    @Override
    public List<StockAuditDTO> getAuditTrail(Long productId) {
        return auditRepository.findByProductId(productId).stream().map(audit -> {
            StockAuditDTO dto = new StockAuditDTO();
            dto.setProductId(audit.getProductId());
            dto.setOldQuantity(audit.getOldQuantity());
            dto.setNewQuantity(audit.getNewQuantity());
            dto.setAction(audit.getAction());
            dto.setPerformedBy(audit.getPerformedBy());
            dto.setPerformedAt(audit.getPerformedAt());
            dto.setWarehouseId(audit.getWarehouseId());
            return dto;
        }).collect(Collectors.toList());
    }
}