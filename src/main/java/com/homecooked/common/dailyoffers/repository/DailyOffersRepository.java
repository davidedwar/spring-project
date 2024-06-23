package com.homecooked.common.dailyoffers.repository;

import com.homecooked.common.dailyoffers.entity.DailyOffers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyOffersRepository extends JpaRepository<DailyOffers, Integer> {

    Integer countByChefId(Integer chefId);

    List<DailyOffers> findByChefId(Integer chefId);

    boolean existsByProductId(Integer productId);
}
