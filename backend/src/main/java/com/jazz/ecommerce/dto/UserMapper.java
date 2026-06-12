/**
 * Maps between User entity and its corresponding DTO classes.
 *
 * Responsibilities:
 * - Convert User entity into UserResponse for API output.
 * - Convert UserRegistration request into a User entity.
 */

package com.jazz.ecommerce.dto;

import org.springframework.stereotype.Component;

import com.jazz.ecommerce.entity.User;

@Component
public class UserMapper {
    public UserResponse toResponseDTO(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public User toEntity(UserRegistration dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole("USER");
        user.setCreatedAt(java.time.OffsetDateTime.now());
        return user;
    }
}
