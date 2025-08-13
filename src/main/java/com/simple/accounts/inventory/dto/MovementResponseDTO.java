package com.simple.accounts.inventory.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MovementResponseDTO {
    private Long id;
    private String productName;
    private BigDecimal quantity;
    private LocalDateTime timestamp;
}
