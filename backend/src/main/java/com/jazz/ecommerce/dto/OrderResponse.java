package com.jazz.ecommerce.dto;

import com.jazz.ecommerce.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Schema(description = "Response payload containing details of an order")
public class OrderResponse {

    @Schema(description = "Unique identifier of the order", example = "1")
    private Long id;

    @Schema(description = "ID of the user who placed the order", example = "1")
    private Long userId;

    @Schema(description = "Total monetary value of the order", example = "2599.98")
    private BigDecimal totalAmount;

    @Schema(description = "Current status of the order", example = "CREATED")
    private OrderStatus status;

    @Schema(description = "Timestamp when the order was created")
    private OffsetDateTime createdAt;

    @Schema(description = "List of items included in the order")
    private List<OrderItemResponse> items;

    @Data
    @Schema(description = "Details of a single item within an order")
    public static class OrderItemResponse {
        @Schema(description = "Unique identifier of the order item", example = "1")
        private Long id;
        @Schema(description = "Quantity of the product ordered", example = "2")
        private Integer quantity;
        @Schema(description = "Price of a single unit of the product at the time of order", example = "1299.99")
        private BigDecimal unitPrice;
        @Schema(description = "Basic details of the product ordered")
        private ProductInfo product;
    }

    @Data
    @Schema(description = "Basic product information for an order item")
    public static class ProductInfo {
        @Schema(description = "Unique identifier of the product", example = "1")
        private Long id;
        @Schema(description = "Name of the product", example = "Laptop Pro 15")
        private String name;
        @Schema(description = "SKU of the product", example = "PROD-12345")
        private String sku;
    }
}
