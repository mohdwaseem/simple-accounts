package com.simple.accounts.inventory.model;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "brands")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", length = 300)
    private String description;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
