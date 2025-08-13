package com.simple.accounts.inventory.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "uom_conversions")
@Data
@EntityListeners(AuditingEntityListener.class)
public class UomConversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_uom_id", nullable = false)
    private UnitOfMeasure fromUom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_uom_id", nullable = false)
    private UnitOfMeasure toUom;

    @Column(name = "conversion_factor", nullable = false)
    private Double conversionFactor;
    // e.g., 1 fromUom = conversionFactor toUom

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
