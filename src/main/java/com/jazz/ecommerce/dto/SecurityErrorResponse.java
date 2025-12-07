/**
 * Error response structure for security-related failures such as:
 * - 401 Unauthorized
 * - 403 Forbidden
 *
 * Used by JwtAuthenticationEntryPoint and JwtAccessDeniedHandler.
 */

package com.jazz.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class SecurityErrorResponse {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> details;
}
