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

    private BigDecimal quantity;
    @Enumerated(EnumType.STRING)
    private MovementType type;
    private LocalDateTime timestamp = LocalDateTime.now();
}
