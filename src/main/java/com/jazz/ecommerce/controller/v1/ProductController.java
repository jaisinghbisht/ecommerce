package com.jazz.ecommerce.controller.v1;

import com.jazz.ecommerce.dto.ProductMapper;
import com.jazz.ecommerce.dto.ProductRequest;
import com.jazz.ecommerce.dto.ProductResponse;
import com.jazz.ecommerce.entity.Product;
import com.jazz.ecommerce.service.ProductService;

import com.jazz.ecommerce.util.ProductSpecifications;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Validated
@RequestMapping("/api/v1/products")
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
    public ResponseEntity<Page<ProductResponse>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Specification<Product> spec = Specification.unrestricted();

        if (name != null) {
            spec = spec.and(ProductSpecifications.nameContains(name));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.minPrice(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.maxPrice(maxPrice));
        }

        Page<ProductResponse> response =
                productService.getProducts(spec, pageable)
                        .map(mapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        Product newProduct = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(newProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        Product updated = productService.updateProduct(id, request);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
