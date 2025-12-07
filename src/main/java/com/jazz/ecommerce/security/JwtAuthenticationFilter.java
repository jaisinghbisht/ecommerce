/**
 * JWT authentication filter that intercepts every HTTP request.
 *
 * Responsibilities:
 * - Extract the JWT from the Authorization header.
 * - Validate the token and extract the username.
 * - Load the user details and set the authentication in the SecurityContext.
 *
 * This filter executes before UsernamePasswordAuthenticationFilter
 * so that JWT authentication happens for every request to secured endpoints.
 */

package com.jazz.ecommerce.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jazz.ecommerce.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        log.info("Raw Authorization header: {}", authHeader);
        log.info("Extracted JWT: {}", jwt);

        try {
            userEmail = jwtService.extractUsername(jwt);
            log.info("Extracted username from JWT: {}", userEmail);
        } catch (Exception ex) {
            log.error("FAILED to extract username from JWT: {}", ex.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            boolean valid = jwtService.isTokenValid(jwt, userDetails);
            log.info("Token validation result: {}", valid);

            if (valid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                log.info("Authentication set for user: {}", userEmail);
            } else {
                log.warn("JWT IS NOT VALID");
            }
        }

        filterChain.doFilter(request, response);
    }
}
