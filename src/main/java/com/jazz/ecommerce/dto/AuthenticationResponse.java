/**
 * Response returned after successful authentication.
 * Contains the JWT access token and optional refresh token.
 */

package com.jazz.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private String refreshToken;
}
