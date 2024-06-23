package com.homecooked.common.reviews.service;

import com.homecooked.common.chef.Chef;
import com.homecooked.common.chef.ChefService;
import com.homecooked.common.order.OrderRepository;
import com.homecooked.common.order.ProductOrder;
import com.homecooked.common.reviews.ReviewsMapper;
import com.homecooked.common.reviews.dto.ReviewsCreateUpdateDto;
import com.homecooked.common.reviews.entity.ChefAverageRating;
import com.homecooked.common.reviews.entity.Reviews;
import com.homecooked.common.reviews.projection.ChefReviewsProjection;
import com.homecooked.common.reviews.repository.ChefAverageRatingViewRepository;
import com.homecooked.common.reviews.repository.ReviewsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewsService {

    private final ReviewsRepository reviewsRepository;
    private final ChefAverageRatingViewRepository chefAverageRatingViewRepository;
    private final ChefService chefService;
    private final OrderRepository orderRepository;
    private final ReviewsMapper reviewsMapper;

    public Page<ChefReviewsProjection> getReviewsForChef(Pageable pageable) {
        Chef chef = chefService.currentChef();
        return reviewsRepository.findReviewsByChefId(chef.getId(), pageable);
    }

    public Double getChefAverageRating() {
        Chef chef = chefService.currentChef();
        return chefAverageRatingViewRepository.findById(chef.getId())
                .map(ChefAverageRating::getAverageRating)
                .orElse(0.0);
    }

    public void addReview(Integer orderId, ReviewsCreateUpdateDto createUpdateDto) {
        ProductOrder order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(String.format("no order with %d id found", orderId)));
        Reviews review = reviewsMapper.dtotoEntity(createUpdateDto);
        review.setOrder(order);
        reviewsRepository.save(review);
    }

    public void updateReview(Integer reviewId, ReviewsCreateUpdateDto createUpdateDto) {
        Reviews review = reviewsRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException(String.format("no review with id %d found", reviewId)));
        reviewsMapper.updateEntityFromDto(createUpdateDto, review);
        reviewsRepository.save(review);
    }

    public void deleteReview(Integer reviewId) {
        Reviews review = reviewsRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException(String.format("no review with id %d found", reviewId)));
        reviewsRepository.delete(review);
    }
}
