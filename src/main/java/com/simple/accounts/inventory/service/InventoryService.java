package com.simple.accounts.inventory.service;

import com.simple.accounts.inventory.dto.MovementCreateDTO;
import com.simple.accounts.inventory.dto.MovementResponseDTO;
import com.simple.accounts.inventory.dto.mapper.InventoryMapper;
import com.simple.accounts.inventory.model.StockMovement;
import com.simple.accounts.inventory.repository.ProductRepository;
import com.simple.accounts.inventory.repository.StockMovementRepository;
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