package com.jazz.ecommerce.service;

import com.jazz.ecommerce.entity.Category;
import com.jazz.ecommerce.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jazz.ecommerce.api.exception.ConflictException;
import com.jazz.ecommerce.api.exception.NotFoundException;
import com.jazz.ecommerce.dto.ProductMapper;
import com.jazz.ecommerce.dto.ProductRequest;
import com.jazz.ecommerce.entity.Product;
import com.jazz.ecommerce.repository.ProductRepository;
import com.jazz.ecommerce.util.ProductSpecifications;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Product createProduct(ProductRequest request) {
        if (productRepository.findBySku(request.getSku()).isPresent()) {
            throw new ConflictException("Product with SKU " + request.getSku() + " already exists.");
        }
        Product product = mapper.toEntity(request);

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category with ID " + request.getCategoryId() + " not found"));
            product.setCategory(category);
        }

        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with ID " + id + " not found"));
    }

    public Page<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Specification<Product> spec = Specification.where(null); // Start with a no-op spec

        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecifications.nameContains(name));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.minPrice(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.maxPrice(maxPrice));
        }

        return productRepository.findAll(spec, pageable);
    }

    @Transactional
    public Product updateProduct(Long id, ProductRequest request) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    mapper.updateEntity(existingProduct, request);

                    if (request.getCategoryId() != null) {
                        Category category = categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new NotFoundException("Category with ID " + request.getCategoryId() + " not found"));
                        existingProduct.setCategory(category);
                    } else {
                        existingProduct.setCategory(null);
                    }

                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new NotFoundException("Product with ID " + id + " not found"));
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }
}