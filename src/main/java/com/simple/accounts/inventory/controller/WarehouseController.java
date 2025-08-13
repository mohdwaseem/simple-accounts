package com.simple.accounts.inventory.controller;

import com.simple.accounts.inventory.model.Warehouse;
import com.simple.accounts.inventory.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseRepository warehouseRepository;

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) {
        if (warehouseRepository.existsByName(warehouse.getName())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(warehouseRepository.save(warehouse));
    }

    @GetMapping
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouse(@PathVariable Long id) {
        return warehouseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id, @RequestBody Warehouse warehouse) {
        return warehouseRepository.findById(id)
                .map(existing -> {
                    existing.setName(warehouse.getName());
                    existing.setAddress(warehouse.getAddress());
                    return ResponseEntity.ok(warehouseRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        if (!warehouseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        warehouseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
