package com.simple.accounts.inventory.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "barcode", length = 100, unique = true)
    private String barcode;

    @Column(name = "sku", length = 100, unique = true)
    private String sku;

    @Column(name = "active")
    private Boolean active = true;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "min_stock_level")
    private Integer minStockLevel;

    @Column(name = "max_stock_level")
    private Integer maxStockLevel;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id")
    private UnitOfMeasure unitOfMeasure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductPrice> prices;

    @OneToMany(mappedBy = "product")
    private List<StockMovement> stockMovements;
}