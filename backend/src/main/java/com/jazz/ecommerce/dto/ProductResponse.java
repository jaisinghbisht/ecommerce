package com.jazz.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
@Schema(description = "Response payload for a product")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {

    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;

    @Schema(description = "Stock Keeping Unit", example = "PROD-12345")
    private String sku;

    @Schema(description = "Name of the product", example = "Laptop Pro 15")
    private String name;

    @Schema(description = "Detailed description of the product", example = "A high-performance laptop for professionals.")
    private String description;

    @Schema(description = "Price of the product", example = "1299.99")
    private BigDecimal price;

    @Schema(description = "Timestamp when the product was created")
    private OffsetDateTime createdAt;

    @Schema(description = "The category this product belongs to")
    private CategoryInfo category;

    @Data
    @Schema(description = "Basic information about a category")
    public static class CategoryInfo {
        @Schema(description = "Unique identifier of the category", example = "1")
        private Long id;
        @Schema(description = "Name of the category", example = "Electronics")
        private String name;
    }
}
