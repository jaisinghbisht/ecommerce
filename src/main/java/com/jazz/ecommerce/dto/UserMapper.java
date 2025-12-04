package com.jazz.ecommerce.dto;

import org.springframework.stereotype.Component;

import com.jazz.ecommerce.model.User;

@Component
public class UserMapper {
    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public User toEntity(UserRegistrationDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole("USER");
        user.setCreatedAt(java.time.OffsetDateTime.now());
        return user;
    }
}
