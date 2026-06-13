package com.jazz.ecommerce.service;

import com.jazz.ecommerce.api.exception.ConflictException;
import com.jazz.ecommerce.api.exception.NotFoundException;
import com.jazz.ecommerce.dto.ProductMapper;
import com.jazz.ecommerce.dto.ProductRequest;
import com.jazz.ecommerce.entity.Category;
import com.jazz.ecommerce.entity.Product;
import com.jazz.ecommerce.repository.CategoryRepository;
import com.jazz.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void whenCreateProduct_withUniqueSku_shouldReturnProduct() {
        // Arrange
        ProductRequest request = new ProductRequest();
        request.setSku("SKU123");
        request.setName("New Product");
        request.setPrice(BigDecimal.TEN);
        request.setCategoryId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setSku("SKU123");

        Category category = new Category();
        category.setId(1L);

        when(productRepository.findBySku("SKU123")).thenReturn(Optional.empty());
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productMapper.toEntity(request)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        // Act
        Product createdProduct = productService.createProduct(request);

        // Assert
        assertNotNull(createdProduct);
        assertEquals("SKU123", createdProduct.getSku());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void whenCreateProduct_withExistingSku_shouldThrowConflictException() {
        // Arrange
        ProductRequest request = new ProductRequest();
        request.setSku("SKU123");
        when(productRepository.findBySku("SKU123")).thenReturn(Optional.of(new Product()));

        // Act & Assert
        assertThrows(ConflictException.class, () -> productService.createProduct(request));
        verify(productRepository, never()).save(any());
    }

    @Test
    void whenGetProductById_withExistingId_shouldReturnProduct() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product foundProduct = productService.getProductById(1L);

        // Assert
        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getId());
    }

    @Test
    void whenGetProductById_withNonExistingId_shouldThrowNotFoundException() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> productService.getProductById(1L));
    }
}
