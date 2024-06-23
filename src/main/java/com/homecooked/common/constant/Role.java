package com.homecooked.common.constant;

import java.util.Objects;

public enum Role {
    CLIENT, CHEF;

    public static Role fromDomain(String domain) {
        if (Objects.isNull(domain)) return null;

        return switch (domain.toLowerCase()) {
            case "cl" -> CLIENT;
            case "ch" -> CHEF;
            default -> throw new RuntimeException("Invalid Domain");
        };

    }
}
