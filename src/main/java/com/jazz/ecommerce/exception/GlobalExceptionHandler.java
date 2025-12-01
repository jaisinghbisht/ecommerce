package com.jazz.ecommerce.exception;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception for request {} {}", request.getMethod(), request.getRequestURI(), ex);

        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        body.put("timestamp", OffsetDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getMessage() == null ? "Unexpected error" : ex.getMessage());
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, status);
    }
}
