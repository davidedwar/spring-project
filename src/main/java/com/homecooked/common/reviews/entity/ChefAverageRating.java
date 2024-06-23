package com.homecooked.common.reviews.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@NoArgsConstructor
@Getter
@Immutable
@Table(name = "chef_average_rating")
public class ChefAverageRating {

    @Id
    Integer chefId;

    Double averageRating;
}
