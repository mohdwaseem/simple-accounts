package com.simple.accounts.inventory.dto.mapper;

import com.simple.accounts.inventory.dto.ProductCreateDTO;
import com.simple.accounts.inventory.dto.ProductResponseDTO;
import com.simple.accounts.inventory.dto.PriceCreateDTO;
import com.simple.accounts.inventory.dto.PriceResponseDTO;
import com.simple.accounts.inventory.dto.MovementCreateDTO;
import com.simple.accounts.inventory.dto.MovementResponseDTO;
import com.simple.accounts.inventory.dto.UnitOfMeasureDTO;
import com.simple.accounts.inventory.dto.UomConversionDTO;
import com.simple.accounts.inventory.dto.ProductCategoryDTO;
import com.simple.accounts.inventory.dto.BrandDTO;
import com.simple.accounts.inventory.dto.ProductSerialDTO;
import com.simple.accounts.inventory.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE) // Optional: Change to WARN or ERROR
public interface InventoryMapper {
    // Product Serial mappings
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "stockMovement.id", source = "stockMovementId")
    ProductSerial toProductSerial(ProductSerialDTO dto);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "stockMovementId", source = "stockMovement.id")
    ProductSerialDTO toProductSerialDTO(ProductSerial entity);
    // Category and Brand mappings
    ProductCategoryDTO toProductCategoryDTO(ProductCategory category);
    BrandDTO toBrandDTO(Brand brand);

    // UOM Conversion mappings
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "fromUom.id", source = "fromUomId")
    @Mapping(target = "toUom.id", source = "toUomId")
    UomConversion toUomConversion(UomConversionDTO dto);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "fromUomId", source = "fromUom.id")
    @Mapping(target = "toUomId", source = "toUom.id")
    UomConversionDTO toUomConversionDTO(UomConversion entity);

    // Product mappings


    @Mapping(target = "active", constant = "true") // Default value
    @Mapping(target = "prices", ignore = true)     // Typically managed separately
    @Mapping(target = "stockMovements", ignore = true) // Typically managed separately
    @Mapping(target = "unitOfMeasure", source = "unitOfMeasureId", qualifiedByName = "idToUnitOfMeasure")
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "idToProductCategory")
    @Mapping(target = "brand", source = "brandId", qualifiedByName = "idToBrand")
    Product toProduct(ProductCreateDTO dto);


    @Mapping(target = "currentStock", ignore = true) // Will be set separately in service
    @Mapping(target = "unitOfMeasure", source = "unitOfMeasure")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "brand", source = "brand")
    ProductResponseDTO toProductResponse(Product product);
    @Named("idToProductCategory")
    default ProductCategory idToProductCategory(Long id) {
        if (id == null) return null;
        ProductCategory category = new ProductCategory();
        category.setId(id);
        return category;
    }

    @Named("idToBrand")
    default Brand idToBrand(Long id) {
        if (id == null) return null;
        Brand brand = new Brand();
        brand.setId(id);
        return brand;
    }
    UnitOfMeasureDTO toUnitOfMeasureDTO(UnitOfMeasure uom);

    @Named("idToUnitOfMeasure")
    default UnitOfMeasure idToUnitOfMeasure(Long id) {
        if (id == null) return null;
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(id);
        return uom;
    }

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
    @Mapping(target = "batchNumber", source = "batchNumber")
    @Mapping(target = "expiryDate", source = "expiryDate")
    StockMovement toStockMovement(MovementCreateDTO dto);

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "batchNumber", source = "batchNumber")
    @Mapping(target = "expiryDate", source = "expiryDate")
    MovementResponseDTO toMovementResponse(StockMovement movement);

    @Named("idToProduct")
    default Product idToProduct(Long id) {
        if (id == null) return null;
        Product product = new Product();
        product.setId(id);
        return product;
    }
}