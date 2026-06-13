package com.jazz.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request payload for creating or updating a product")
public class ProductRequest {

    @Schema(description = "Stock Keeping Unit", example = "PROD-12345", required = true)
    @NotBlank(message = "SKU is required")
    @Size(min = 3, max = 50, message = "SKU must be between 3 and 50 characters")
    private String sku;

    @Schema(description = "Name of the product", example = "Laptop Pro 15", required = true)
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @Schema(description = "Detailed description of the product", example = "A high-performance laptop for professionals.")
    @Size(max = 1000, message = "Description can be up to 1000 characters")
    private String description;

    @Schema(description = "Price of the product", example = "1299.99", required = true)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be positive")
    private BigDecimal price;

    @Schema(description = "ID of the category this product belongs to", example = "1")
    private Long categoryId;
}
