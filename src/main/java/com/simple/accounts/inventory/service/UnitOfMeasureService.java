package com.simple.accounts.inventory.service;

import com.simple.accounts.inventory.dto.UnitOfMeasureDTO;
import com.simple.accounts.inventory.dto.mapper.InventoryMapper;
import com.simple.accounts.inventory.model.UnitOfMeasure;
import com.simple.accounts.inventory.repository.UnitOfMeasureRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UnitOfMeasureService {
    private final UnitOfMeasureRepository uomRepo;
    private final InventoryMapper mapper;

    public UnitOfMeasureDTO create(UnitOfMeasureDTO dto) {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setName(dto.getName());
        uom.setAbbreviation(dto.getAbbreviation());
        uom.setDescription(dto.getDescription());
        return mapper.toUnitOfMeasureDTO(uomRepo.save(uom));
    }

    @Transactional(readOnly = true)
    public List<UnitOfMeasureDTO> list() {
        return uomRepo.findAll().stream().map(mapper::toUnitOfMeasureDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UnitOfMeasureDTO get(Long id) {
        UnitOfMeasure uom = uomRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("UOM not found: " + id));
        return mapper.toUnitOfMeasureDTO(uom);
    }

    public void delete(Long id) {
        uomRepo.deleteById(id);
    }
}
