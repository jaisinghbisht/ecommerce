/**
 * Exception thrown when a requested resource cannot be found.
 *
 * <p>Used for cases such as missing products, users, or other domain objects.
 *
 * <p>Translated to a structured 404 Not Found response by GlobalExceptionHandler.
 */

package com.jazz.ecommerce.api.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
