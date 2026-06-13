package com.jazz.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request payload for creating or updating a category")
public class CategoryRequest {

    @Schema(description = "Name of the category", example = "Electronics", required = true)
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;

    @Schema(description = "Detailed description of the category", example = "Gadgets and devices")
    @Size(max = 1000, message = "Description can be up to 1000 characters")
    private String description;
}
