package com.simple.accounts.inventory.service;

import com.simple.accounts.inventory.dto.ProductSerialDTO;
import com.simple.accounts.inventory.dto.mapper.InventoryMapper;
import com.simple.accounts.inventory.model.ProductSerial;
import com.simple.accounts.inventory.repository.ProductSerialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductSerialService {
    private final ProductSerialRepository serialRepo;
    private final InventoryMapper mapper;

    public ProductSerialDTO create(ProductSerialDTO dto) {
        ProductSerial entity = mapper.toProductSerial(dto);
        return mapper.toProductSerialDTO(serialRepo.save(entity));
    }

    @Transactional(readOnly = true)
    public List<ProductSerialDTO> listByProduct(Long productId) {
        return serialRepo.findByProductId(productId).stream()
                .map(mapper::toProductSerialDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductSerialDTO get(Long id) {
        ProductSerial entity = serialRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serial not found: " + id));
        return mapper.toProductSerialDTO(entity);
    }

    public void delete(Long id) {
        serialRepo.deleteById(id);
    }
}
