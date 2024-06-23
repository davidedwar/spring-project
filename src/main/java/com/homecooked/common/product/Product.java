package com.homecooked.common.product;

import com.homecooked.common.chef.Chef;
import com.homecooked.common.productcategory.ProductCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@Table(name = "products", indexes = {
        @Index(name = "idx_product_name", columnList = "name"),
        @Index(name = "idx_product_ingredients", columnList = "ingredients"),
        @Index(name = "idx_product_description", columnList = "description")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Chef chef;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "categoryId", foreignKey = @ForeignKey(name = "fk_categoryId"))
    private ProductCategory category;

    private Integer minimumQuantity = 1;

    private Integer noticePeriod;

    private BigDecimal price;

    private Integer depositPercentageRequired;

    private String ingredients;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
