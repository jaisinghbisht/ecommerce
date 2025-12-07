/**
 * Servlet filter that ensures every incoming HTTP request has a correlation ID.
 *
 * <p>Responsibilities:
 * <ul>
 *     <li>Read the "X-Correlation-Id" header from the incoming request.</li>
 *     <li>If missing, generate a new UUID-based correlation ID.</li>
 *     <li>Store the value in MDC so it is included in all application logs.</li>
 *     <li>Attach the correlation ID to the outgoing HTTP response.</li>
 * </ul>
 *
 * <p>This enables distributed tracing across microservices and makes debugging easier
 * because every log entry for a request contains a shared correlation ID.
 */

package com.jazz.ecommerce.api;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import java.io.IOException;
import java.util.UUID;

public class CorrelationIdFilter implements Filter {

    public static final String HEADER_NAME = "X-Correlation-Id";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String correlationId = request.getHeader(HEADER_NAME);
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put(HEADER_NAME, correlationId);
        response.setHeader(HEADER_NAME, correlationId);

        chain.doFilter(req, res);

        MDC.remove(HEADER_NAME);
    }
}
