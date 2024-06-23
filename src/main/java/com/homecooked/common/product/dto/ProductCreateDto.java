package com.homecooked.common.product.dto;

import com.homecooked.common.addons.dto.AddOnsCreateUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductCreateDto {
    private Integer chefId;
    private String name;
    private String description;
    private Integer categoryId;
    private Integer minimumQuantity = 1;
    private Integer noticePeriod;
    private BigDecimal price;
    private Integer depositPercentageRequired;
    private String ingredients;
    List<AddOnsCreateUpdateDto> specifications;

}
