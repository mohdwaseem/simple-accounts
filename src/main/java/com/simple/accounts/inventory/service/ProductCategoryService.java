package com.simple.accounts.inventory.service;

import com.simple.accounts.inventory.dto.ProductCategoryDTO;
import com.simple.accounts.inventory.dto.mapper.InventoryMapper;
import com.simple.accounts.inventory.model.ProductCategory;
import com.simple.accounts.inventory.repository.ProductCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCategoryService {

    public ProductCategoryDTO update(ProductCategoryDTO dto) {
        ProductCategory entity = categoryRepo.findById(dto.getId())
            .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.getId()));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return mapper.toProductCategoryDTO(categoryRepo.save(entity));
    }
    private final ProductCategoryRepository categoryRepo;
    private final InventoryMapper mapper;

    public ProductCategoryDTO create(ProductCategoryDTO dto) {
        ProductCategory entity = new ProductCategory();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return mapper.toProductCategoryDTO(categoryRepo.save(entity));
    }

    @Transactional(readOnly = true)
    public List<ProductCategoryDTO> list() {
        return categoryRepo.findAll().stream().map(mapper::toProductCategoryDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductCategoryDTO get(Long id) {
        ProductCategory entity = categoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found: " + id));
        return mapper.toProductCategoryDTO(entity);
    }

    public void delete(Long id) {
        categoryRepo.deleteById(id);
    }
}
