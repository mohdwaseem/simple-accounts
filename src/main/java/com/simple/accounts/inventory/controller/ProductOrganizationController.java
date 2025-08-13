package com.simple.accounts.inventory.controller;

import com.simple.accounts.inventory.dto.BrandDTO;
import com.simple.accounts.inventory.dto.ProductCategoryDTO;
import com.simple.accounts.inventory.service.BrandService;
import com.simple.accounts.inventory.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class ProductOrganizationController {
    private final ProductCategoryService categoryService;
    private final BrandService brandService;

    // Category endpoints
    @PostMapping("/categories")
    public ResponseEntity<ProductCategoryDTO> createCategory(@RequestBody ProductCategoryDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }
    @PutMapping("/categories/{id}")
    public ResponseEntity<ProductCategoryDTO> updateCategory(@PathVariable Long id, @RequestBody ProductCategoryDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(categoryService.update(dto));
    }
    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategoryDTO>> listCategories() {
        return ResponseEntity.ok(categoryService.list());
    }
    @GetMapping("/categories/{id}")
    public ResponseEntity<ProductCategoryDTO> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.get(id));
    }
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Brand endpoints
    @PostMapping("/brands")
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO dto) {
        return ResponseEntity.ok(brandService.create(dto));
    }
    @PutMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable Long id, @RequestBody BrandDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(brandService.update(dto));
    }
    @GetMapping("/brands")
    public ResponseEntity<List<BrandDTO>> listBrands() {
        return ResponseEntity.ok(brandService.list());
    }
    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> getBrand(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.get(id));
    }
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
