package com.simple.accounts.inventory.service;

import com.simple.accounts.inventory.dto.MovementCreateDTO;
import com.simple.accounts.inventory.dto.MovementResponseDTO;
import com.simple.accounts.inventory.dto.mapper.InventoryMapper;
import com.simple.accounts.inventory.model.StockMovement;
import com.simple.accounts.inventory.repository.ProductRepository;
import com.simple.accounts.inventory.repository.StockMovementRepository;
import com.simple.accounts.inventory.repository.StockEntryRepository;
import com.simple.accounts.inventory.model.StockEntry;
import com.simple.accounts.inventory.model.enums.ValuationMethod;
import com.simple.accounts.inventory.service.ProductSerialService;
import com.simple.accounts.inventory.dto.ProductSerialDTO;
import com.simple.accounts.inventory.repository.ProductSerialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional; // Spring version
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final StockMovementRepository movementRepo;
    private final ProductRepository productRepo;
    private final InventoryMapper mapper;
    private final StockEntryRepository stockEntryRepo;
    private final ProductSerialService productSerialService;
    private final ProductSerialRepository productSerialRepository;
    /**
     * Calculate inventory value for a product using the specified valuation method.
     */
    @Transactional(readOnly = true)
    public double calculateInventoryValue(Long productId, ValuationMethod method) {
        List<StockEntry> entries = stockEntryRepo.findByProductIdOrderByEntryDateAsc(productId);
        switch (method) {
            case FIFO:
                return calculateFIFO(entries);
            case LIFO:
                return calculateLIFO(entries);
            case WEIGHTED_AVERAGE:
                return calculateWeightedAverage(entries);
            default:
                throw new IllegalArgumentException("Unknown valuation method");
        }
    }

    private double calculateFIFO(List<StockEntry> entries) {
        double value = 0.0;
        for (StockEntry entry : entries) {
            value += entry.getQuantity() * entry.getUnitCost();
        }
        return value;
    }

    private double calculateLIFO(List<StockEntry> entries) {
        double value = 0.0;
        List<StockEntry> reversed = new java.util.ArrayList<>(entries);
        java.util.Collections.reverse(reversed);
        for (StockEntry entry : reversed) {
            value += entry.getQuantity() * entry.getUnitCost();
        }
        return value;
    }

    private double calculateWeightedAverage(List<StockEntry> entries) {
        int totalQty = 0;
        double totalCost = 0.0;
        for (StockEntry entry : entries) {
            totalQty += entry.getQuantity();
            totalCost += entry.getQuantity() * entry.getUnitCost();
        }
        return totalQty == 0 ? 0.0 : totalCost / totalQty * totalQty;
    }

    public MovementResponseDTO recordMovement(MovementCreateDTO dto) {
        // Validate input
        if (dto.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // Verify product exists
        productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Create and save movement
        StockMovement movement = mapper.toStockMovement(dto);
        movement.setTimestamp(LocalDateTime.now());
        StockMovement savedMovement = movementRepo.save(movement);

        // Serial integration: create serials if provided
        if (dto.getSerialNumbers() != null && !dto.getSerialNumbers().isEmpty()) {
            for (String serial : dto.getSerialNumbers()) {
                // Prevent duplicate serials for the same product
                boolean exists = productSerialRepository.findBySerialNumberAndProductId(serial, dto.getProductId()).isPresent();
                if (exists) {
                    throw new IllegalArgumentException("Duplicate serial number for this product: " + serial);
                }
                ProductSerialDTO serialDTO = new ProductSerialDTO();
                serialDTO.setSerialNumber(serial);
                serialDTO.setProductId(dto.getProductId());
                serialDTO.setStockMovementId(savedMovement.getId());
                serialDTO.setActive(true);
                productSerialService.create(serialDTO);
            }
        }

        return mapper.toMovementResponse(savedMovement);
    }

    @Transactional(readOnly = true)
    public BigDecimal getStockLevel(Long productId) {
        BigDecimal stock = movementRepo.calculateStockLevel(productId);
        return stock != null ? stock : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public List<MovementResponseDTO> listMovements(Long productId, String type) {
        List<StockMovement> movements;
        if (productId != null && type != null) {
            movements = movementRepo.findByProductIdAndType(productId, type);
        } else if (productId != null) {
            movements = movementRepo.findByProductId(productId);
        } else if (type != null) {
            movements = movementRepo.findByType(type);
        } else {
            movements = movementRepo.findAll();
        }
        return movements.stream().map(mapper::toMovementResponse).toList();
    }

    @Transactional(readOnly = true)
    public MovementResponseDTO getMovement(Long movementId) {
        StockMovement movement = movementRepo.findById(movementId)
                .orElseThrow(() -> new EntityNotFoundException("Movement not found with ID: " + movementId));
        return mapper.toMovementResponse(movement);
    }

    public void deleteMovement(Long movementId) {
        StockMovement movement = movementRepo.findById(movementId)
                .orElseThrow(() -> new EntityNotFoundException("Movement not found with ID: " + movementId));
        movementRepo.delete(movement);
    }
}