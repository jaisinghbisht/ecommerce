/**
 * Exception for indicating authentication failure or missing credentials.
 *
 * <p>Commonly thrown when a user attempts to access a protected resource
 * without a valid JWT token or when login credentials are invalid.
 *
 * <p>Handled by GlobalExceptionHandler and converted into a 401 Unauthorized response.
 */

package com.jazz.ecommerce.api.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
