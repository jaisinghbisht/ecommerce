package com.jazz.ecommerce.controller.v1;

import com.jazz.ecommerce.dto.OrderRequest;
import com.jazz.ecommerce.dto.OrderResponse;
import com.jazz.ecommerce.entity.Order;
import com.jazz.ecommerce.entity.OrderItem;
import com.jazz.ecommerce.entity.OrderStatus;
import com.jazz.ecommerce.entity.User;
import com.jazz.ecommerce.service.CurrentUserService;
import com.jazz.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order APIs", description = "APIs for managing customer orders")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class OrderController {

    private final OrderService orderService;
    private final CurrentUserService currentUserService;

    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        User user = currentUserService.getCurrentUser();
        Order order = orderService.createOrder(orderRequest, user);
        return new ResponseEntity<>(toResponse(order), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all orders for the authenticated user")
    public ResponseEntity<Page<OrderResponse>> getOrdersForUser(Pageable pageable) {
        User user = currentUserService.getCurrentUser();
        Page<OrderResponse> orders = orderService.getOrdersForUser(user, pageable).map(this::toResponse);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single order by ID")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        User user = currentUserService.getCurrentUser();
        Order order = orderService.getOrderById(id, user);
        return ResponseEntity.ok(toResponse(order));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update the status of an order")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatus status) {
        User user = currentUserService.getCurrentUser();
        Order updatedOrder = orderService.updateOrderStatus(id, status, user);
        return ResponseEntity.ok(toResponse(updatedOrder));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        User user = currentUserService.getCurrentUser();
        orderService.deleteOrder(id, user);
        return ResponseEntity.noContent().build();
    }

    // --- Helper Method for DTO Conversion ---
    private OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUser().getId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setItems(order.getItems().stream().map(this::toOrderItemResponse).collect(Collectors.toList()));
        return response;
    }

    private OrderResponse.OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        OrderResponse.OrderItemResponse itemResponse = new OrderResponse.OrderItemResponse();
        itemResponse.setId(orderItem.getId());
        itemResponse.setQuantity(orderItem.getQuantity());
        itemResponse.setUnitPrice(orderItem.getUnitPrice());

        OrderResponse.ProductInfo productInfo = new OrderResponse.ProductInfo();
        productInfo.setId(orderItem.getProduct().getId());
        productInfo.setName(orderItem.getProduct().getName());
        productInfo.setSku(orderItem.getProduct().getSku());
        itemResponse.setProduct(productInfo);

        return itemResponse;
    }
}