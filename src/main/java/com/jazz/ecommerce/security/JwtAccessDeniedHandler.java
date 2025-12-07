/**
 * Handles 403 Forbidden errors for authenticated users who lack required permissions.
 * Returns a structured JSON error response instead of the default HTML error page.
 */

package com.jazz.ecommerce.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazz.ecommerce.dto.SecurityErrorResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void handle(HttpServletRequest request,
                        HttpServletResponse response,
                        AccessDeniedException accessDeniedException) throws IOException, ServletException {

                SecurityErrorResponse err = SecurityErrorResponse.builder()
                                .timestamp(OffsetDateTime.now())
                                .status(HttpServletResponse.SC_FORBIDDEN)
                                .error("Forbidden")
                                .message(accessDeniedException.getMessage() != null ? accessDeniedException.getMessage()
                                                : "Access denied")
                                .path(request.getRequestURI())
                                .build();

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(response.getWriter(), err);
        }
}
