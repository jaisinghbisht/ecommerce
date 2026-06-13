package com.jazz.ecommerce.service;

import com.jazz.ecommerce.dto.AuthenticationRequest;
import com.jazz.ecommerce.dto.AuthenticationResponse;
import com.jazz.ecommerce.entity.RefreshToken;
import com.jazz.ecommerce.entity.User;
import com.jazz.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void whenLogin_withValidCredentials_shouldReturnTokens() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        UserDetails userDetails = mock(UserDetails.class);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("mockRefreshToken");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("mockAccessToken");
        when(refreshTokenService.createRefreshToken(1L)).thenReturn(refreshToken);

        // Act
        AuthenticationResponse response = authenticationService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("mockAccessToken", response.getToken());
        assertEquals("mockRefreshToken", response.getRefreshToken());
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    void whenLogin_withNonExistingUser_shouldThrowIllegalStateException() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("nouser@example.com");
        request.setPassword("password");

        when(userRepository.findByEmail("nouser@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> authenticationService.login(request));
    }
}
