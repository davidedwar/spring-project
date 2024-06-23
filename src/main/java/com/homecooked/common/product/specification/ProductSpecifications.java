package com.homecooked.common.product.specification;

import com.homecooked.common.chef.Chef;
import com.homecooked.common.product.Product;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<Product> hasChefLike(String chefName) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Chef> chefJoin = root.join("chef");
            return criteriaBuilder.like(criteriaBuilder.lower(chefJoin.get("fullName")), "%" + chefName.toLowerCase() + "%");
        };
    }

    public static Specification<Product> hasChefId(Integer chefId) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Chef> chefJoin = root.join("chef");
            return criteriaBuilder.equal(chefJoin.get("id"), chefId);
        };
    }

    public static Specification<Product> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> isFromCategory(Integer categoryId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }


}
