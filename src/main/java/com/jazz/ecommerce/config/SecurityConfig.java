/**
 * Main security configuration for HTTP requests.
 *
 * Responsibilities:
 * - Defines which endpoints are public and which require authentication.
 * - Registers the JWT authentication filter into the Spring Security filter chain.
 * - Configures exception handling for 401 (unauthorized) and 403 (forbidden) errors.
 * - Disables session creation (stateless mode) since JWT is used for authentication.
 *
 * This class controls Spring Security behavior, NOT the JWT creation logic.
 */

package com.jazz.ecommerce.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jazz.ecommerce.security.JwtAccessDeniedHandler;
import com.jazz.ecommerce.security.JwtAuthenticationEntryPoint;
import com.jazz.ecommerce.security.JwtAuthenticationFilter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;
        private final JwtAuthenticationEntryPoint authenticationEntryPoint;
        private final JwtAccessDeniedHandler accessDeniedHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**", "/actuator/health").permitAll()
                                                .anyRequest().authenticated())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(authenticationEntryPoint)
                                                .accessDeniedHandler(accessDeniedHandler))
                                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
