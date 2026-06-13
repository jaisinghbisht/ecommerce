package com.jazz.ecommerce.dto;

import com.jazz.ecommerce.entity.Category;
import org.springframework.stereotype.Component;

import com.jazz.ecommerce.entity.Product;

import java.time.OffsetDateTime;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        Product p = new Product();
        p.setSku(request.getSku());
        p.setName(request.getName());
        p.setDescription(request.getDescription());
        p.setPrice(request.getPrice());
        p.setCreatedAt(OffsetDateTime.now());
        // Category must be set in the service layer
        return p;
    }

    public void updateEntity(Product existing, ProductRequest request) {
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        // Category must be updated in the service layer
    }

    public ProductResponse toResponse(Product product) {
        ProductResponse r = new ProductResponse();
        r.setId(product.getId());
        r.setSku(product.getSku());
        r.setName(product.getName());
        r.setDescription(product.getDescription());
        r.setPrice(product.getPrice());
        r.setCreatedAt(product.getCreatedAt());

        if (product.getCategory() != null) {
            ProductResponse.CategoryInfo categoryInfo = new ProductResponse.CategoryInfo();
            categoryInfo.setId(product.getCategory().getId());
            categoryInfo.setName(product.getCategory().getName());
            r.setCategory(categoryInfo);
        }

        return r;
    }
}
