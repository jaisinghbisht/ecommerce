/**
 * REST controller that exposes CRUD operations for managing products.
 *
 * <p>Endpoints include:
 * <ul>
 *     <li>GET /products/{id} – fetch a product by ID</li>
 *     <li>GET /products – list all products</li>
 *     <li>POST /products – create a new product</li>
 *     <li>PUT /products/{id} – update an existing product</li>
 *     <li>DELETE /products/{id} – remove a product</li>
 * </ul>
 *
 * <p>Validates incoming requests and delegates business logic to ProductService.
 */

package com.jazz.ecommerce.controller;

import com.jazz.ecommerce.entity.Product;
import com.jazz.ecommerce.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Validated
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public List<Product> list() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product p) {
        Product newProduct = productService.createProduct(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable @NotNull(message = "ID cannot be null") Long id,
            @Valid @RequestBody Product updatedProduct) {
        Product product = productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull(message = "ID cannot be null") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
