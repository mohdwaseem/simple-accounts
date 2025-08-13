package com.simple.accounts.inventory.controller;

import com.simple.accounts.inventory.dto.UomConversionDTO;
import com.simple.accounts.inventory.service.UomConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uom-conversions")
@RequiredArgsConstructor
public class UomConversionController {
    private final UomConversionService conversionService;

    @PostMapping
    public ResponseEntity<UomConversionDTO> create(@RequestBody UomConversionDTO dto) {
        return ResponseEntity.ok(conversionService.create(dto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<UomConversionDTO>> listByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(conversionService.listByProduct(productId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UomConversionDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(conversionService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        conversionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
