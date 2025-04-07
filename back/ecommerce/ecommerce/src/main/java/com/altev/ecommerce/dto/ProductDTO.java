package com.altev.ecommerce.dto;


import com.altev.ecommerce.entity.enums.InventoryStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.altev.ecommerce.entity.enums.InventoryStatus;

public record ProductDTO(
        Long id,
        String code,
        String name,
        String description,
        String image,
        String category,
        Double price,
        Integer quantity,
        String internalReference,
        Long shellId,
        InventoryStatus inventoryStatus,
        Integer rating
) {}
