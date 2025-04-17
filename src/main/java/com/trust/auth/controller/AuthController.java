package com.trust.auth.controller;

import com.trust.auth.dto.LoginRequest;
import com.trust.auth.model.User;
import com.trust.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
}
