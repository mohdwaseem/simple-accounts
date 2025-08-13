package com.simple.accounts.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PriceCreateDTO {
    @NotBlank
    private String priceListName;
    @Positive
    private BigDecimal price;
    private LocalDate validFrom;
    private LocalDate validTo;
    private String currency;
}
