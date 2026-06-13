package com.jazz.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Request payload for creating a new order")
public class OrderRequest {

    @Schema(description = "List of items to include in the order", required = true)
    @NotEmpty
    @Valid
    private List<OrderItemRequest> items;
}
