package com.simple.accounts.inventory.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PriceResponseDTO {
    private Long id;
    private String priceListName;
    private BigDecimal price;
    private Long productId;
    private String productName;
}
