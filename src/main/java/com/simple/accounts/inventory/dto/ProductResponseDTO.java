package com.simple.accounts.inventory.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductResponseDTO {
    public Long id;
    public String code;
    public String name;
    public List<PriceResponseDTO> prices;
    public BigDecimal currentStock;
}
