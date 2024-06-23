package com.homecooked.common.reviews.controller;

import com.homecooked.common.reviews.projection.ChefReviewsProjection;
import com.homecooked.common.reviews.service.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ch/api/reviews")
@RequiredArgsConstructor
public class ReviewsChefController {

    private final ReviewsService reviewsService;

    @GetMapping
    public Page<ChefReviewsProjection> allReviews(Pageable pageable) {
        return reviewsService.getReviewsForChef(pageable);
    }

    @GetMapping("/average-rating")
    public Double chefAverageRating() {
        return reviewsService.getChefAverageRating();
    }

}
