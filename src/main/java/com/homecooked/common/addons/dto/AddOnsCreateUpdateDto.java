package com.homecooked.common.addons.dto;

import java.math.BigDecimal;

public record AddOnsCreateUpdateDto(
        String name,
        BigDecimal additionalCost,
        String description
) {
}
