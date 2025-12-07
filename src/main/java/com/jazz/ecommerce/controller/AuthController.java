/**
 * REST controller responsible for handling authentication-related endpoints.
 *
 * <p>Currently supports:
 * <ul>
 *     <li>/api/auth/login – authenticates a user and returns JWT tokens</li>
 * </ul>
 *
 * <p>Delegates authentication logic to AuthenticationService and returns
 * structured authentication responses.
 */

package com.jazz.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jazz.ecommerce.dto.AuthenticationRequest;
import com.jazz.ecommerce.dto.AuthenticationResponse;
import com.jazz.ecommerce.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
