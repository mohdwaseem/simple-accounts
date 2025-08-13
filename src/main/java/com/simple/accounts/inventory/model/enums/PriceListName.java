package com.simple.accounts.inventory.model.enums;

import lombok.Getter;

@Getter
public enum PriceListName {
    RETAIL("Retail"),
    WHOLESALE("Wholesale"),
    VIP("VIP");

    private final String displayName;

    PriceListName(String displayName) {
        this.displayName = displayName;
    }

}