/**
 * Service responsible for handling user authentication (login).
 *
 * <p>Workflow:
 * <ul>
 *     <li>Authenticates email/password using AuthenticationManager.</li>
 *     <li>Fetches the user from the database.</li>
 *     <li>Generates a JWT access token for API authentication.</li>
 *     <li>Creates and stores a refresh token for session renewal.</li>
 *     <li>Returns both tokens to the client.</li>
 * </ul>
 *
 * <p>This service does NOT validate JWTs during requests—that is done by JwtAuthenticationFilter.
 */

package com.jazz.ecommerce.service;

import com.jazz.ecommerce.dto.AuthenticationRequest;
import com.jazz.ecommerce.dto.AuthenticationResponse;
import com.jazz.ecommerce.entity.RefreshToken;
import com.jazz.ecommerce.entity.User;
import com.jazz.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;

    public AuthenticationResponse login(AuthenticationRequest request) {
        logger.info("Attempting to authenticate user: {}", request.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
            logger.info("User {} authenticated successfully", request.getEmail());
        } catch (Exception e) {
            logger.error("Authentication failed for user {}: {}", request.getEmail(), e.getMessage());
            throw e;
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("User not found after authentication"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        String accessToken = jwtService.generateToken(userDetails);
        logger.info("Generated access token for user {}", request.getEmail());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        logger.info("Generated refresh token for user {}", request.getEmail());

        return AuthenticationResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }
}
