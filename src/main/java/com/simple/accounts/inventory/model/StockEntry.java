package com.simple.accounts.inventory.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_entries")
@Data
public class StockEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unitCost;

    @Column(nullable = false)
    private LocalDateTime entryDate;

    @Column(name = "batch_number", length = 100)
    private String batchNumber;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;
}
