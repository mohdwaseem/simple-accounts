package com.simple.accounts.inventory.dto;

import lombok.Data;

@Data
public class UomConversionDTO {
    private Long id;
    private Long productId;
    private Long fromUomId;
    private Long toUomId;
    private Double conversionFactor;
}
