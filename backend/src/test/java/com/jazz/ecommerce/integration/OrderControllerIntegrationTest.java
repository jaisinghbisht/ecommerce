package com.jazz.ecommerce.integration;

import com.jazz.ecommerce.dto.OrderItemRequest;
import com.jazz.ecommerce.dto.OrderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class OrderControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithUserDetails("user@example.com") // This user is seeded in V2__seed_users.sql
    void shouldCreateOrderSuccessfullyForAuthenticatedUser() throws Exception {
        // Arrange
        // Products are seeded by V3__seed_products.sql
        OrderItemRequest item1 = new OrderItemRequest();
        item1.setProductId(1L);
        item1.setQuantity(1);

        OrderItemRequest item2 = new OrderItemRequest();
        item2.setProductId(2L);
        item2.setQuantity(2);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItems(List.of(item1, item2));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.totalAmount").isNumber()) // Verify total is calculated, but don't assert a specific value due to random seed data
                .andExpect(jsonPath("$.items.length()").value(2));
    }
}