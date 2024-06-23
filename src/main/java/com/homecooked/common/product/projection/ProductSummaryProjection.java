package com.homecooked.common.product.projection;

import java.math.BigDecimal;

public interface ProductSummaryProjection {
    String getName();

    BigDecimal getPrice();

    Long getId();


}
