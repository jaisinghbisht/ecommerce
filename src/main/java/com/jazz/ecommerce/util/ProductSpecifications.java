package com.jazz.ecommerce.util;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.jazz.ecommerce.entity.Product;

public final class ProductSpecifications {

    private ProductSpecifications() {}

    public static Specification<Product> nameContains(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> minPrice(BigDecimal price) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> maxPrice(BigDecimal price) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("price"), price);
    }
}
