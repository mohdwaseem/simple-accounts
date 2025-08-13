package com.simple.accounts.inventory.model.enums;

public enum MovementType {
    PURCHASE,    // Inventory increase (vendor purchase)
    SALE,        // Inventory decrease (customer sale)
    ADJUSTMENT,  // Manual correction
    RETURN,      // Customer return
    LOSS         // Damaged/expired items
}
