package com.simple.accounts.inventory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MovementCreateDTO {
    @NotNull private Long productId;
    @Positive private BigDecimal quantity;
    @NotNull private String type; // Use String or enum as needed
}
