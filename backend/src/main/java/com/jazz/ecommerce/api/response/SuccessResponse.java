/**
 * Standard success wrapper used for returning API responses in a consistent format.
 *
 * <p>Contains:
 * <ul>
 *     <li>correlationId – carried over from incoming request for tracing/logging</li>
 *     <li>message – optional success message</li>
 *     <li>data – the actual payload being returned</li>
 * </ul>
 *
 * <p>Useful for maintaining uniform API output and future extensibility.
 */

package com.jazz.ecommerce.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse<T> {
    private String correlationId;
    private String message;
    private T data;
}
