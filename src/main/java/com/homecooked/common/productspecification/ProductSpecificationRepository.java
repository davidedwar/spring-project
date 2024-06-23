package com.homecooked.common.productspecification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Integer> {
    List<ProductSpecification> findByProductId(Integer productId);

}
