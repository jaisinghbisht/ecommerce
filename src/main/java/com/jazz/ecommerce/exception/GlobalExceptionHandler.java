package com.jazz.ecommerce.exception;

import com.jazz.ecommerce.dto.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Bad credentials (invalid username/password)
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest req) {
		ErrorResponse err = baseError(HttpStatus.UNAUTHORIZED, "Invalid credentials", ex.getMessage(),
				req.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}

	// User not found
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFound(UsernameNotFoundException ex, HttpServletRequest req) {
		ErrorResponse err = baseError(HttpStatus.UNAUTHORIZED, "User not found", ex.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}

	// Token expired
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> handleExpiredJwt(ExpiredJwtException ex, HttpServletRequest req) {
		ErrorResponse err = baseError(HttpStatus.UNAUTHORIZED, "Token expired", ex.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}

	// Signature or other jwt parsing errors
	@ExceptionHandler({ SignatureException.class, JwtException.class })
	public ResponseEntity<ErrorResponse> handleJwtParseExceptions(Exception ex, HttpServletRequest req) {
		ErrorResponse err = baseError(HttpStatus.UNAUTHORIZED, "Invalid token", ex.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}

	// Access denied (user authenticated but not authorized)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
		ErrorResponse err = baseError(HttpStatus.FORBIDDEN, "Access denied", ex.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	// Validation errors (Bad request)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
		List<String> details = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map((FieldError fe) -> fe.getField() + ": " + fe.getDefaultMessage())
				.collect(Collectors.toList());

		ErrorResponse err = ErrorResponse.builder()
				.timestamp(OffsetDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error("Validation failed")
				.message("Request validation failed")
				.path(req.getRequestURI())
				.details(details)
				.build();

		return ResponseEntity.badRequest().body(err);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAll(Exception ex, HttpServletRequest req) {
		ErrorResponse err = baseError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex.getMessage(),
				req.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

	private ErrorResponse baseError(HttpStatus status, String shortError, String message, String path) {
		return ErrorResponse.builder()
				.timestamp(OffsetDateTime.now())
				.status(status.value())
				.error(shortError)
				.message(message)
				.path(path)
				.build();
	}
}
