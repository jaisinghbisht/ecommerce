package com.jazz.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Schema(description = "Response payload for a category")
public class CategoryResponse {

    @Schema(description = "Unique identifier of the category", example = "1")
    private Long id;

    @Schema(description = "Name of the category", example = "Electronics")
    private String name;

    @Schema(description = "Detailed description of the category", example = "Gadgets and devices")
    private String description;

    @Schema(description = "Timestamp when the category was created")
    private OffsetDateTime createdAt;
}
