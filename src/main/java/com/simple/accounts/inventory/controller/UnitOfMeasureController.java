package com.simple.accounts.inventory.controller;

import com.simple.accounts.inventory.dto.UnitOfMeasureDTO;
import com.simple.accounts.inventory.service.UnitOfMeasureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uom")
@RequiredArgsConstructor
public class UnitOfMeasureController {
    private final UnitOfMeasureService uomService;

    @PostMapping
    public ResponseEntity<UnitOfMeasureDTO> create(@RequestBody UnitOfMeasureDTO dto) {
        return ResponseEntity.ok(uomService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<UnitOfMeasureDTO>> list() {
        return ResponseEntity.ok(uomService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitOfMeasureDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(uomService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        uomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
