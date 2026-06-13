package com.jazz.ecommerce.integration;

import com.jazz.ecommerce.dto.CategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CategoryControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldCreateCategorySuccessfully() throws Exception {
        // Arrange
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Integration Test Category");
        categoryRequest.setDescription("A category for integration tests");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Integration Test Category"));
    }
}
