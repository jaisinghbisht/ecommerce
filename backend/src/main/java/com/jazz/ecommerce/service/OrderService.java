package com.jazz.ecommerce.service;

import com.jazz.ecommerce.api.exception.NotFoundException;
import com.jazz.ecommerce.dto.OrderRequest;
import com.jazz.ecommerce.entity.*;
import com.jazz.ecommerce.repository.OrderRepository;
import com.jazz.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrder(OrderRequest orderRequest, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (var itemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product with ID " + itemRequest.getProductId() + " not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(product.getPrice()); // Use the authoritative price from the database

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Page<Order> getOrdersForUser(User user, Pageable pageable) {
        return orderRepository.findByUserId(user.getId(), pageable);
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id, User user) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order with ID " + id + " not found"));

        // Ensure a user can only access their own orders
        if (!order.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("Order with ID " + id + " not found for this user");
        }
        return order;
    }

    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus newStatus, User user) {
        Order order = getOrderById(id, user);
        // In a real application, you would have complex logic here to check for valid status transitions
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id, User user) {
        Order order = getOrderById(id, user);
        orderRepository.delete(order);
    }
}
