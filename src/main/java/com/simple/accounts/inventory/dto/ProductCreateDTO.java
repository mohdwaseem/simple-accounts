package com.simple.accounts.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductCreateDTO {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String description;
}
