package com.homecooked.common.addons;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "specifications")
public class AddOns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private BigDecimal additionalCost;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
