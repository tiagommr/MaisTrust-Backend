package com.trust.auth.controller;

import com.trust.auth.dto.LoginRequest;
import com.trust.auth.dto.RegisterRequest;
import com.trust.auth.model.Role;
import com.trust.auth.model.User;
import com.trust.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        System.out.println("🚀 Tentativa de registo recebida:");
        System.out.println("Nome: " + request.getNome());
        System.out.println("Email: " + request.getEmail());
        System.out.println("Password: " + request.getPassword());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            System.out.println("⚠️ Email já registado!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já está registado.");
        }

        if (!isPasswordValid(request.getPassword())) {
            System.out.println("❌ Password inválida!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password não cumpre os critérios.");
        }

        try {
            User user = User.builder()
                    .nome(request.getNome())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .telefone(request.getTelefone())
                    .tipoUtilizador(request.getTipoUtilizador())
                    .idClube(request.getIdClube())
                    .idFederacao(request.getIdFederacao())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            userRepository.save(user);
            System.out.println("✅ Utilizador registado com sucesso.");
            return ResponseEntity.ok("Registo realizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registar utilizador.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                user.setPassword(null);
                return ResponseEntity.ok(user);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    private boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\S]{8,}$");
    }
}