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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;
        private final UserRepository userRepository;
        private final RefreshTokenService refreshTokenService;
        private final UserDetailsService userDetailsService;

        public AuthenticationResponse login(AuthenticationRequest request) {

                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new IllegalStateException("User not found"));

                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

                String accessToken = jwtService.generateToken(userDetails);

                RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

                return AuthenticationResponse.builder()
                                .token(accessToken)
                                .refreshToken(refreshToken.getToken())
                                .build();
        }
}
