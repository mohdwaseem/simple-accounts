package com.simple.accounts.inventory.model;

import com.simple.accounts.inventory.model.enums.PriceListName;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "product_prices")
@Data
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
    @Enumerated(EnumType.STRING)
    private PriceListName priceListName;
    private BigDecimal price;
    private String currency = "SAR";

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;
}
