/**
 * Service for creating and managing refresh tokens.
 *
 * <p>Responsibilities:
 * <ul>
 *     <li>Generate secure refresh tokens using SecureRandom.</li>
 *     <li>Associate tokens with a user and store them in the database.</li>
 *     <li>Set expiration time based on application configuration.</li>
 *     <li>Verify whether a given refresh token has expired.</li>
 * </ul>
 *
 * <p>Refresh tokens allow long-lived sessions and issuing new JWT access tokens.
 */

package com.jazz.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jazz.ecommerce.entity.RefreshToken;
import com.jazz.ecommerce.repository.RefreshTokenRepository;
import com.jazz.ecommerce.repository.UserRepository;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${application.security.jwt.refresh-expiration-ms:600000}")
    private long refreshExpirationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshToken createRefreshToken(Long userId) {

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        String refreshTokenValue = generateSecureToken();

        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshTokenValue)
                .user(user)
                .expiryDate(OffsetDateTime.now().plus(Duration.ofMillis(refreshExpirationMs)))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    // Generate cryptographically strong token
    private String generateSecureToken() {
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public boolean isExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isBefore(OffsetDateTime.now());
    }
}
