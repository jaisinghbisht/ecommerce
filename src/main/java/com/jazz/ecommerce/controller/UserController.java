package com.jazz.ecommerce.controller;

import com.jazz.ecommerce.dto.UserMapper;
import com.jazz.ecommerce.dto.UserResponseDTO;
import com.jazz.ecommerce.model.User;
import com.jazz.ecommerce.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // @GetMapping("/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@Valid @PathVariable String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }
}