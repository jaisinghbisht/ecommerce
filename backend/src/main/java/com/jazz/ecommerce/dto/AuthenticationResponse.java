package com.jazz.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Response payload after successful authentication")
public class AuthenticationResponse {

    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1Ni...")
    private String token;

    @Schema(description = "JWT refresh token (optional)", example = "eyJhbGciOiJIUzI1Ni...")
    private String refreshToken;
}
