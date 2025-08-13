
package com.simple.accounts.inventory.controller;

import com.simple.accounts.inventory.service.BarcodeService;
import org.springframework.http.MediaType;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

import com.simple.accounts.inventory.dto.MovementCreateDTO;
import com.simple.accounts.inventory.dto.MovementResponseDTO;
import com.simple.accounts.inventory.service.InventoryService;
import com.simple.accounts.inventory.service.InventoryReportService;
import com.simple.accounts.inventory.dto.InventoryReportDTO;
import com.simple.accounts.inventory.dto.InventoryAgingDTO;
import com.simple.accounts.inventory.service.InventoryAgingService;
import com.simple.accounts.inventory.model.enums.ValuationMethod;
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
    private final InventoryReportService inventoryReportService;
    private final InventoryAgingService inventoryAgingService;
    private final BarcodeService barcodeService;
    @GetMapping(value = "/barcode/{code}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getBarcode(@PathVariable String code) throws Exception {
        var image = barcodeService.generateBarcode(code, 400, 100);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        }
    }

    @GetMapping(value = "/qrcode/{code}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getQRCode(@PathVariable String code) throws Exception {
        var image = barcodeService.generateQRCode(code, 300);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        }
    }
    @GetMapping("/aging-report")
    public ResponseEntity<List<InventoryAgingDTO>> getAgingReport(@RequestParam(required = false) Integer periodDays) {
        return ResponseEntity.ok(inventoryAgingService.getAgingReport(periodDays));
    }
    @GetMapping("/report")
    public ResponseEntity<List<InventoryReportDTO>> getInventoryReport(
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId) {
        return ResponseEntity.ok(inventoryReportService.getInventoryReport(warehouseId, categoryId, brandId));
    }

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

    @GetMapping("/products/{productId}/value")
    public ResponseEntity<Double> getInventoryValue(
            @PathVariable Long productId,
            @RequestParam ValuationMethod method) {
        double value = inventoryService.calculateInventoryValue(productId, method);
        return ResponseEntity.ok(value);
    }
}