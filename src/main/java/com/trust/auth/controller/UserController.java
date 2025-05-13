package com.trust.auth.controller;

import com.trust.auth.dto.UserResponse;
import com.trust.auth.model.User;
import com.trust.auth.repository.UserRepository;
import com.trust.auth.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String token = jwtService.getJwtFromRequest(request); // Obter o token do cabeçalho
        System.out.println("🔐 Token recebido: " + token);

        if (token != null && jwtService.isTokenValid(token)) {
            Long userId = jwtService.getUserIdFromToken(token); // Extrai o ID do usuário do token
            System.out.println("👤 UserID extraído: " + userId);
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                System.out.println("📤 Nome: " + user.getNome());
                UserResponse response = new UserResponse(
                        user.getId(),
                        user.getNome(),
                        user.getEmail(),
                        user.getTipoUtilizador()
                );
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
