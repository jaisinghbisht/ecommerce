package com.jazz.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "An item within an order request")
public class OrderItemRequest {

    @Schema(description = "ID of the product being ordered", required = true, example = "1")
    @NotNull
    private Long productId;

    @Schema(description = "Number of units of the product to order", required = true, example = "2")
    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
