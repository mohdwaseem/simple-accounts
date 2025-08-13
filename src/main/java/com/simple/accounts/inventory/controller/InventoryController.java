package com.simple.accounts.inventory.controller;

import com.simple.accounts.inventory.dto.MovementCreateDTO;
import com.simple.accounts.inventory.dto.MovementResponseDTO;
import com.simple.accounts.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/movements")
    public ResponseEntity<MovementResponseDTO> recordMovement(
            @Valid @RequestBody MovementCreateDTO dto) {
        return ResponseEntity.ok(inventoryService.recordMovement(dto));
    }

    @GetMapping("/products/{productId}/stock")
    public ResponseEntity<BigDecimal> getStockLevel(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getStockLevel(productId));
    }

    @GetMapping("/movements")
    public ResponseEntity<List<MovementResponseDTO>> listMovements(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String type // e.g., IN, OUT, ADJUSTMENT
    ) {
        // You need to implement listMovements in InventoryService with optional filters
        return ResponseEntity.ok(inventoryService.listMovements(productId, type));
    }

    @GetMapping("/movements/{movementId}")
    public ResponseEntity<MovementResponseDTO> getMovement(@PathVariable Long movementId) {
        // You need to implement getMovement in InventoryService
        return ResponseEntity.ok(inventoryService.getMovement(movementId));
    }

    @DeleteMapping("/movements/{movementId}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long movementId) {
        // You need to implement deleteMovement in InventoryService
        inventoryService.deleteMovement(movementId);
        return ResponseEntity.noContent().build();
    }
}