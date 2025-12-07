package com.jazz.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jazz.ecommerce.api.exception.ConflictException;
import com.jazz.ecommerce.api.exception.NotFoundException;
import com.jazz.ecommerce.dto.ProductMapper;
import com.jazz.ecommerce.dto.ProductRequest;
import com.jazz.ecommerce.entity.Product;
import com.jazz.ecommerce.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Product createProduct(ProductRequest request) {

        if (productRepository.findBySku(request.getSku()).isPresent()) {
            throw new ConflictException("Product with SKU " + request.getSku() + " already exists.");
        }

        Product product = mapper.toEntity(request);
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with ID " + id + " not found"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product updateProduct(Long id, ProductRequest request) {

        return productRepository.findById(id)
                .map(existingProduct -> {
                    mapper.updateEntity(existingProduct, request);
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
