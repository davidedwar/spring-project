package com.homecooked.common.reviews.controller;

import com.homecooked.common.reviews.dto.ReviewsCreateUpdateDto;
import com.homecooked.common.reviews.service.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cl/api/reviews")
@RequiredArgsConstructor
public class ReviewsClientController {

    private final ReviewsService reviewsService;

    @PostMapping("/orders/{id}")
    public void addReview(@PathVariable Integer orderId, @RequestBody ReviewsCreateUpdateDto createUpdateDto) {
        reviewsService.addReview(orderId, createUpdateDto);
    }

    @PatchMapping("{id}")
    public void updateReview(@PathVariable Integer reviewId, @RequestBody ReviewsCreateUpdateDto createUpdateDto) {
        reviewsService.updateReview(reviewId, createUpdateDto);
    }

    @DeleteMapping("{id}")
    public void deleteReview(@PathVariable Integer reviewId) {
        reviewsService.deleteReview(reviewId);
    }
}
