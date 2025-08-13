package com.simple.accounts.inventory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

import java.time.LocalDateTime;

@Data
public class MovementCreateDTO {
    @NotNull private Long productId;
    @Positive private BigDecimal quantity;
    @NotNull private String type; // Use String or enum as needed

    private String batchNumber;
    private LocalDateTime expiryDate;

    // For serial tracking
    private java.util.List<String> serialNumbers;
}
