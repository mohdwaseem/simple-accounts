package com.simple.accounts.inventory.dto.mapper;

import com.simple.accounts.inventory.dto.ProductCreateDTO;
import com.simple.accounts.inventory.dto.ProductResponseDTO;
import com.simple.accounts.inventory.dto.PriceCreateDTO;
import com.simple.accounts.inventory.dto.PriceResponseDTO;
import com.simple.accounts.inventory.dto.MovementCreateDTO;
import com.simple.accounts.inventory.dto.MovementResponseDTO;
import com.simple.accounts.inventory.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE) // Optional: Change to WARN or ERROR
public interface InventoryMapper {

    // Product mappings

    @Mapping(target = "active", constant = "true") // Default value
    @Mapping(target = "prices", ignore = true)     // Typically managed separately
    @Mapping(target = "stockMovements", ignore = true) // Typically managed separately
    Product toProduct(ProductCreateDTO dto);

    @Mapping(target = "currentStock", ignore = true) // Will be set separately in service
    ProductResponseDTO toProductResponse(Product product);

    // Price mappings
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "currency", defaultValue = "SAR")
    ProductPrice toProductPrice(PriceCreateDTO dto);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    PriceResponseDTO toPriceResponse(ProductPrice price);

    // Movement mappings
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true) // Will be set automatically
    @Mapping(target = "product", source = "productId", qualifiedByName = "idToProduct")
    StockMovement toStockMovement(MovementCreateDTO dto);

    @Mapping(target = "productName", source = "product.name")
    MovementResponseDTO toMovementResponse(StockMovement movement);

    @Named("idToProduct")
    default Product idToProduct(Long id) {
        if (id == null) return null;
        Product product = new Product();
        product.setId(id);
        return product;
    }
}