package com.jazz.ecommerce.controller;

import com.jazz.ecommerce.dto.ProductMapper;
import com.jazz.ecommerce.dto.ProductRequest;
import com.jazz.ecommerce.dto.ProductResponse;
import com.jazz.ecommerce.entity.Product;
import com.jazz.ecommerce.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper mapper;

    public ProductController(ProductService productService, ProductMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(mapper.toResponse(product));
    }

    @GetMapping
    public List<ProductResponse> list() {
        return productService.getAllProducts()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        Product newProduct = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(newProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        Product updated = productService.updateProduct(id, request);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
