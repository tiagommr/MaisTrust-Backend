package com.trust.auth.controller;

import com.trust.auth.dto.UserResponse;
import com.trust.auth.model.User;
import com.trust.auth.repository.UserRepository;
import com.trust.auth.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String token = jwtService.getJwtFromRequest(request);
        System.out.println("🔐 Token recebido: " + token);

        if (token != null && jwtService.isTokenValid(token)) {
            Long userId = jwtService.getUserIdFromToken(token);
            System.out.println("👤 UserID extraído: " + userId);
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                System.out.println("📤 Nome: " + user.getNome());
                UserResponse response = new UserResponse(
                        user.getId(),
                        user.getNome(),
                        user.getEmail(),
                        user.getTipoUtilizador(),
                        user.getTelefone() // Adiciona este campo ao UserResponse
                );
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setNome(updatedUser.getNome());
            user.setEmail(updatedUser.getEmail());
            user.setTelefone(updatedUser.getTelefone());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            userRepository.save(user);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
