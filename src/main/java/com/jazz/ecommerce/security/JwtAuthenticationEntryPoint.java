/**
 * Entry point for authentication errors.
 * Invoked when an unauthenticated user tries to access a secured endpoint.
 * Returns a JSON 401 Unauthorized response instead of redirecting to a login page.
 */

package com.jazz.ecommerce.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazz.ecommerce.dto.SecurityErrorResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        SecurityErrorResponse err = SecurityErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .error("Unauthorized")
                .message(authException.getMessage() != null ? authException.getMessage() : "Unauthorized")
                .path(request.getRequestURI())
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), err);
    }
}
