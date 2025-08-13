package com.simple.accounts.inventory.service;

import com.simple.accounts.inventory.dto.BrandDTO;
import com.simple.accounts.inventory.dto.mapper.InventoryMapper;
import com.simple.accounts.inventory.model.Brand;
import com.simple.accounts.inventory.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {

    public BrandDTO update(BrandDTO dto) {
        Brand entity = brandRepo.findById(dto.getId())
            .orElseThrow(() -> new EntityNotFoundException("Brand not found: " + dto.getId()));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return mapper.toBrandDTO(brandRepo.save(entity));
    }
    private final BrandRepository brandRepo;
    private final InventoryMapper mapper;

    public BrandDTO create(BrandDTO dto) {
        Brand entity = new Brand();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return mapper.toBrandDTO(brandRepo.save(entity));
    }

    @Transactional(readOnly = true)
    public List<BrandDTO> list() {
        return brandRepo.findAll().stream().map(mapper::toBrandDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BrandDTO get(Long id) {
        Brand entity = brandRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Brand not found: " + id));
        return mapper.toBrandDTO(entity);
    }

    public void delete(Long id) {
        brandRepo.deleteById(id);
    }
}
