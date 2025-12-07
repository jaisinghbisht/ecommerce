/**
 * Standard API error response model used for all exception cases handled by
 * GlobalExceptionHandler.
 *
 * <p>Contains:
 * <ul>
 *     <li>timestamp – when the error occurred</li>
 *     <li>correlationId – unique request identifier for tracing</li>
 *     <li>errorCode – application-specific error code (e.g., NOT_FOUND)</li>
 *     <li>message – human-readable error description</li>
 *     <li>details – additional validation or contextual messages</li>
 * </ul>
 *
 * <p>This class ensures consistent error structure across the entire API.
 */

package com.jazz.ecommerce.api.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String correlationId;
    private String errorCode;
    private String message;
    private List<String> details;
}
