package com.simple.accounts.inventory.dto;

import lombok.Data;

@Data
public class ProductSerialDTO {
    private Long id;
    private String serialNumber;
    private Long productId;
    private Long stockMovementId;
    private Boolean active;
}
