
package com.simple.accounts.inventory.service;

import com.simple.accounts.exception.BusinessException;
import com.simple.accounts.inventory.dto.PriceCreateDTO;
import com.simple.accounts.inventory.dto.PriceResponseDTO;
import com.simple.accounts.inventory.dto.ProductCreateDTO;
import com.simple.accounts.inventory.dto.ProductResponseDTO;
import com.simple.accounts.inventory.dto.mapper.InventoryMapper;
import com.simple.accounts.inventory.model.Product;
import com.simple.accounts.inventory.model.ProductPrice;
import com.simple.accounts.inventory.model.enums.PriceListName;
import com.simple.accounts.inventory.repository.ProductPriceRepository;
import com.simple.accounts.inventory.repository.ProductRepository;
import com.simple.accounts.inventory.repository.NotificationRepository;
import com.simple.accounts.inventory.repository.UnitOfMeasureRepository;
import com.simple.accounts.inventory.model.UnitOfMeasure;
import com.simple.accounts.inventory.model.Notification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    @Transactional(readOnly = true)
    public ProductResponseDTO getProductByBarcode(String barcode) {
        Product product = productRepo.findByBarcode(barcode)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with barcode: " + barcode));
        ProductResponseDTO response = mapper.toProductResponse(product);
        response.setCurrentStock(inventoryService.getStockLevel(product.getId()));
        return response;
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getProductBySku(String sku) {
        Product product = productRepo.findBySku(sku)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with SKU: " + sku));
        ProductResponseDTO response = mapper.toProductResponse(product);
        response.setCurrentStock(inventoryService.getStockLevel(product.getId()));
        return response;
    }
    private final ProductRepository productRepo;
    private final ProductPriceRepository priceRepo;
    private final InventoryMapper mapper;
    private final InventoryService inventoryService;
    private final NotificationRepository notificationRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    /**
     * Check all products and create notifications for those below minStockLevel.
     */
    public void checkAndNotifyLowStock() {
        productRepo.findAll().forEach(product -> {
            if (product.getMinStockLevel() != null &&
                inventoryService.getStockLevel(product.getId()).intValue() < product.getMinStockLevel()) {
                String msg = "Product '" + product.getName() + "' (code: " + product.getCode() + ") is below minimum stock level.";
                Notification notification = new Notification();
                notification.setMessage(msg);
                notification.setRead(false);
                notification.setCreatedAt(java.time.LocalDateTime.now());
                notificationRepository.save(notification);
            }
        });
    }

    public ProductResponseDTO createProduct(ProductCreateDTO dto) {
        if (productRepo.existsByCode(dto.getCode())) {
            throw new BusinessException("Product with code " + dto.getCode() + " already exists");
        }
        Product product = mapper.toProduct(dto);
        if (dto.getUnitOfMeasureId() != null) {
            UnitOfMeasure uom = unitOfMeasureRepository.findById(dto.getUnitOfMeasureId())
                .orElseThrow(() -> new EntityNotFoundException("Unit of Measure not found: " + dto.getUnitOfMeasureId()));
            product.setUnitOfMeasure(uom);
        }
        product.setActive(true); // Ensure default active status
        return mapper.toProductResponse(productRepo.save(product));
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getProduct(Long id) {
        Product product = productRepo.findWithPricesById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        ProductResponseDTO response = mapper.toProductResponse(product);
        response.setCurrentStock(inventoryService.getStockLevel(id));
        return response;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> listProducts() {
        return productRepo.findAll().stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO updateProduct(Long id, ProductCreateDTO dto) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        product.setCode(dto.getCode());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        if (dto.getUnitOfMeasureId() != null) {
            UnitOfMeasure uom = unitOfMeasureRepository.findById(dto.getUnitOfMeasureId())
                .orElseThrow(() -> new EntityNotFoundException("Unit of Measure not found: " + dto.getUnitOfMeasureId()));
            product.setUnitOfMeasure(uom);
        } else {
            product.setUnitOfMeasure(null);
        }
        return mapper.toProductResponse(productRepo.save(product));
    }

    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        productRepo.delete(product);
    }

    public ProductResponseDTO setProductActive(Long id, boolean active) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        product.setActive(active);
        return mapper.toProductResponse(productRepo.save(product));
    }

    public PriceResponseDTO addPrice(Long productId, PriceCreateDTO dto) {
        validatePriceDates(dto); // New validation
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
        ProductPrice price = mapper.toProductPrice(dto);
        price.setProduct(product);
        return mapper.toPriceResponse(priceRepo.save(price));
    }

    @Transactional(readOnly = true)
    public List<PriceResponseDTO> getProductPrices(Long productId) {
        return priceRepo.findByProductId(productId).stream()
                .map(mapper::toPriceResponse)
                .toList();
    }

    private void validatePriceDates(PriceCreateDTO dto) {
        if (dto.getValidFrom() != null && dto.getValidTo() != null
                && dto.getValidFrom().isAfter(dto.getValidTo())) {
            throw new BusinessException("Valid From date must be before Valid To date");
        }
    }

    public PriceResponseDTO updatePrice(Long productId, Long priceId, PriceCreateDTO dto) {
    // Product existence check is not needed here as price will be checked for product match below
        ProductPrice price = priceRepo.findById(priceId)
                .orElseThrow(() -> new EntityNotFoundException("Price not found with ID: " + priceId));
        if (!price.getProduct().getId().equals(productId)) {
            throw new BusinessException("Price does not belong to the specified product");
        }
        price.setPriceListName(
                dto.getPriceListName() != null ? PriceListName.valueOf(dto.getPriceListName().toUpperCase()) : null);
        price.setPrice(dto.getPrice());
        price.setValidFrom(dto.getValidFrom());
        price.setValidTo(dto.getValidTo());
        price.setCurrency(dto.getCurrency());
        return mapper.toPriceResponse(priceRepo.save(price));
    }

    public void deletePrice(Long productId, Long priceId) {
    ProductPrice price = priceRepo.findById(priceId)
        .orElseThrow(() -> new EntityNotFoundException("Price not found with ID: " + priceId));
    if (!price.getProduct().getId().equals(productId)) {
        throw new BusinessException("Price does not belong to the specified product");
    }
    priceRepo.delete(price);
    }
}