package com.simple.accounts.inventory.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Data
public class StockMovement {

    public enum MovementType {
        PURCHASE, SALE, ADJUSTMENT, RETURN, LOSS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    private LocalDateTime timestamp;

    @Column(name = "batch_number", length = 100)
    private String batchNumber;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @PrePersist
    public void prePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}