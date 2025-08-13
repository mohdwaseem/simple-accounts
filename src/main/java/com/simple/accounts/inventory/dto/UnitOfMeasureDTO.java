package com.simple.accounts.inventory.dto;

import lombok.Data;

@Data
public class UnitOfMeasureDTO {
    private Long id;
    private String name;
    private String abbreviation;
    private String description;
}
