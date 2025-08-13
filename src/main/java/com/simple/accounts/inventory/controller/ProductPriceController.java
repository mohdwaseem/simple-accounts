package com.simple.accounts.inventory.controller;

import com.simple.accounts.inventory.dto.PriceCreateDTO;
import com.simple.accounts.inventory.dto.PriceResponseDTO;
import com.simple.accounts.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/{productId}/prices")
@RequiredArgsConstructor
public class ProductPriceController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<PriceResponseDTO> addPrice(
            @PathVariable Long productId,
            @Valid @RequestBody PriceCreateDTO dto) {
        return ResponseEntity.ok(productService.addPrice(productId, dto));
    }

    @GetMapping
    public ResponseEntity<List<PriceResponseDTO>> getProductPrices(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductPrices(productId));
    }

    @PutMapping("/{priceId}")
    public ResponseEntity<PriceResponseDTO> updatePrice(
            @PathVariable Long productId,
            @PathVariable Long priceId,
            @Valid @RequestBody PriceCreateDTO dto) {
        // You need to implement updatePrice in ProductService
        return ResponseEntity.ok(productService.updatePrice(productId, priceId, dto));
    }

    @DeleteMapping("/{priceId}")
    public ResponseEntity<Void> deletePrice(
            @PathVariable Long productId,
            @PathVariable Long priceId) {
        // You need to implement deletePrice in ProductService
        productService.deletePrice(productId, priceId);
        return ResponseEntity.noContent().build();
    }
}
