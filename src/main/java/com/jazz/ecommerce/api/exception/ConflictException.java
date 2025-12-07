/**
 * Exception indicating a conflict with the current state of the resource.
 *
 * <p>Typically thrown when attempting to create or update a resource that
 * already exists or violates a uniqueness constraint (e.g., duplicate SKU,
 * duplicate email, etc.).
 *
 * <p>Handled by GlobalExceptionHandler to return a structured 409 CONFLICT response.
 */

package com.jazz.ecommerce.api.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
