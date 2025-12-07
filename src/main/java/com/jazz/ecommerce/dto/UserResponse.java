/**
 * Response DTO used to expose user information safely via the API.
 */

package com.jazz.ecommerce.dto;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String role;
    private OffsetDateTime createdAt;
}