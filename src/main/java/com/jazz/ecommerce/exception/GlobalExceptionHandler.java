package com.jazz.ecommerce.exception;

import org.slf4j.MDC;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.jazz.ecommerce.api.exception.ConflictException;
import com.jazz.ecommerce.api.exception.ForbiddenException;
import com.jazz.ecommerce.api.exception.NotFoundException;
import com.jazz.ecommerce.api.exception.UnauthorizedException;
import com.jazz.ecommerce.api.response.ErrorResponse;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private String correlationId() {
		return MDC.get("X-Correlation-Id");
	}

	private ErrorResponse build(String code, String message) {
		return ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.errorCode(code)
				.message(message)
				.correlationId(correlationId())
				.build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
		var details = ex.getBindingResult().getFieldErrors().stream()
				.map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
				.collect(Collectors.toList());

		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.errorCode("VALIDATION_ERROR")
				.message("Invalid request")
				.details(details)
				.correlationId(correlationId())
				.build();

		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(build("UNAUTHORIZED", ex.getMessage()));
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(build("FORBIDDEN", ex.getMessage()));
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(build("NOT_FOUND", ex.getMessage()));
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(build("CONFLICT", ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
		ex.printStackTrace();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(build("INTERNAL_ERROR", "Something went wrong"));
	}
}
