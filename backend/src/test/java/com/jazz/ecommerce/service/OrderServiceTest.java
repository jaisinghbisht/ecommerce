package com.jazz.ecommerce.service;

import com.jazz.ecommerce.api.exception.NotFoundException;
import com.jazz.ecommerce.dto.OrderItemRequest;
import com.jazz.ecommerce.dto.OrderRequest;
import com.jazz.ecommerce.entity.Order;
import com.jazz.ecommerce.entity.OrderStatus;
import com.jazz.ecommerce.entity.Product;
import com.jazz.ecommerce.entity.User;
import com.jazz.ecommerce.repository.OrderRepository;
import com.jazz.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void whenCreateOrder_withValidProducts_shouldCalculateTotalAndSaveOrder() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setPrice(new BigDecimal("10.00"));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setPrice(new BigDecimal("25.50"));

        OrderItemRequest item1 = new OrderItemRequest();
        item1.setProductId(1L);
        item1.setQuantity(2); // 2 * 10.00 = 20.00

        OrderItemRequest item2 = new OrderItemRequest();
        item2.setProductId(2L);
        item2.setQuantity(1); // 1 * 25.50 = 25.50

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItems(List.of(item1, item2));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Order createdOrder = orderService.createOrder(orderRequest, user);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(user, createdOrder.getUser());
        assertEquals(OrderStatus.CREATED, createdOrder.getStatus());
        assertEquals(2, createdOrder.getItems().size());
        // Expected total: 20.00 + 25.50 = 45.50
        assertEquals(0, new BigDecimal("45.50").compareTo(createdOrder.getTotalAmount()));

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void whenCreateOrder_withNonExistingProduct_shouldThrowNotFoundException() {
        // Arrange
        User user = new User();
        user.setId(1L);

        OrderItemRequest item1 = new OrderItemRequest();
        item1.setProductId(99L); // This product does not exist
        item1.setQuantity(1);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItems(List.of(item1));

        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.createOrder(orderRequest, user));
        verify(orderRepository, never()).save(any());
    }
}
