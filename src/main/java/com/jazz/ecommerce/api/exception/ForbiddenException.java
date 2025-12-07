/**
 * Exception representing a 403 Forbidden condition.
 *
 * <p>Thrown when a user is authenticated but does not have permission to
 * access the requested resource or perform the requested operation.
 *
 * <p>Mapped to a 403 response by GlobalExceptionHandler.
 */

package com.jazz.ecommerce.api.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
