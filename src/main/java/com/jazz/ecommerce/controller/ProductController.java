package com.jazz.ecommerce.controller;

import com.jazz.ecommerce.model.Product;
import com.jazz.ecommerce.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Product> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.notFound().build();
        }
        return repo.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product p) {
        if (p.getCreatedAt() == null) p.setCreatedAt(OffsetDateTime.now());
        return repo.save(p);
    }
}