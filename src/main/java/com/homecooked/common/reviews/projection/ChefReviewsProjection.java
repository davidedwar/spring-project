package com.homecooked.common.reviews.projection;

import java.time.LocalDateTime;

public interface ChefReviewsProjection {

    Integer getOrderId();

    Double getRating();

    String getReview();

    LocalDateTime getCreatedAt();

    String getClientName();
}
