package com.homecooked.common.reviews.repository;

import com.homecooked.common.reviews.entity.Reviews;
import com.homecooked.common.reviews.projection.ChefReviewsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

    @Query("SELECT r.order.id AS orderId, r.rating AS rating, r.review AS review, r.createdAt AS createdAt, c.fullName AS clientName " +
            "FROM Reviews r JOIN r.order o JOIN o.chef ch JOIN o.client c WHERE ch.id = :chefId")
    Page<ChefReviewsProjection> findReviewsByChefId(Integer chefId, Pageable pageable);
}