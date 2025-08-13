package com.simple.accounts.inventory.service;

import com.simple.accounts.inventory.dto.UomConversionDTO;
import com.simple.accounts.inventory.dto.mapper.InventoryMapper;
import com.simple.accounts.inventory.model.UomConversion;
import com.simple.accounts.inventory.repository.UomConversionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UomConversionService {
    private final UomConversionRepository conversionRepo;
    private final InventoryMapper mapper;

    public UomConversionDTO create(UomConversionDTO dto) {
        UomConversion entity = mapper.toUomConversion(dto);
        return mapper.toUomConversionDTO(conversionRepo.save(entity));
    }

    @Transactional(readOnly = true)
    public List<UomConversionDTO> listByProduct(Long productId) {
        return conversionRepo.findByProductId(productId).stream()
                .map(mapper::toUomConversionDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UomConversionDTO get(Long id) {
        UomConversion entity = conversionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UOM Conversion not found: " + id));
        return mapper.toUomConversionDTO(entity);
    }

    public void delete(Long id) {
        conversionRepo.deleteById(id);
    }
}
