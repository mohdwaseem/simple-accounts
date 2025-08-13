package com.simple.accounts.inventory.controller;

import com.simple.accounts.inventory.dto.ProductSerialDTO;
import com.simple.accounts.inventory.service.ProductSerialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serials")
@RequiredArgsConstructor
public class ProductSerialController {
    private final ProductSerialService serialService;

    @PostMapping
    public ResponseEntity<ProductSerialDTO> create(@RequestBody ProductSerialDTO dto) {
        return ResponseEntity.ok(serialService.create(dto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductSerialDTO>> listByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(serialService.listByProduct(productId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductSerialDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(serialService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
