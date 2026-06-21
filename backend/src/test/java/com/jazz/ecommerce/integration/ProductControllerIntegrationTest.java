package com.jazz.ecommerce.integration;

import com.jazz.ecommerce.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class ProductControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateProductSuccessfullyWhenAdmin() throws Exception {
        // Arrange
        ProductRequest productRequest = new ProductRequest();
        productRequest.setSku("INT-TEST-SKU-001");
        productRequest.setName("Integration Test Product");
        productRequest.setPrice(new BigDecimal("99.99"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.sku").value("INT-TEST-SKU-001"))
                .andExpect(jsonPath("$.name").value("Integration Test Product"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnForbiddenWhenUserCreatesProduct() throws Exception {
        // Arrange
        ProductRequest productRequest = new ProductRequest();
        productRequest.setSku("INT-TEST-SKU-002");
        productRequest.setName("Integration Test Product 2");
        productRequest.setPrice(new BigDecimal("19.99"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void shouldReturnProductById() throws Exception {
        // Arrange: The product name is seeded by V3__seed_products.sql
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product 1"));
    }
}