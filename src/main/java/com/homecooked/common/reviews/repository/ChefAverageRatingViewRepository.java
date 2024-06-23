package com.homecooked.common.reviews.repository;

import com.homecooked.common.reviews.entity.ChefAverageRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChefAverageRatingViewRepository extends JpaRepository<ChefAverageRating, Integer> {

}
