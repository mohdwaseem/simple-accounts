package com.simple.accounts.inventory.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_serials", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"serial_number", "product_id"})
})
@Data
public class ProductSerial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", nullable = false, length = 100)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_movement_id")
    private StockMovement stockMovement;

    @Column(name = "active")
    private Boolean active = true;
}
