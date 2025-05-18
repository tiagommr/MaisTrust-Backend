package com.trust.auth.controller;

import com.trust.auth.dto.LoginRequest;
import com.trust.auth.dto.RegisterRequest;
import com.trust.auth.model.User;
import com.trust.auth.model.VerificationToken;
import com.trust.auth.repository.VerificationTokenRepository;
import com.trust.auth.security.JwtService;  // Importando o JwtService para geração de token
import com.trust.auth.repository.UserRepository;
import com.trust.auth.security.LoginResponse;
import com.trust.auth.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;  // Adicionando o serviço JwtService para gerar o token

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;


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

            User savedUser = userRepository.save(user);

// Criar token de verificação
            String token = java.util.UUID.randomUUID().toString();
            VerificationToken verificationToken = VerificationToken.builder()
                    .token(token)
                    .user(savedUser)
                    .expiryDate(LocalDateTime.now().plusDays(1))
                    .build();
            tokenRepository.save(verificationToken);

// Enviar email
            String link = "http://localhost:8080/auth/verify?token=" + token;
            emailService.enviarEmailConfirmacao(savedUser.getEmail(), link);

            System.out.println("✅ Utilizador registado e email de verificação enviado.");
            return ResponseEntity.ok("Registo realizado com sucesso! Verifique o seu email.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registar utilizador.");
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verificarConta(@RequestParam("token") String token) {
        Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);

        if (optionalToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Token inválido.");
        }

        VerificationToken vt = optionalToken.get();
        if (vt.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.GONE).body("Token expirado.");
        }

        User user = vt.getUser();
        user.setVerified(true);
        userRepository.save(user);
        tokenRepository.delete(vt);

        return ResponseEntity.ok("Conta verificada com sucesso!");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
            }

            if (!user.isVerified()) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(Map.of(
                                "status", "not_verified",
                                "message", "Conta ainda não verificada."
                        ));
            }

            String token = jwtService.generateToken(user.getId());
            user.setPassword(null); // Não expor a password
            return ResponseEntity.ok(new LoginResponse(user, token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }


    @PostMapping("/recover")
    public ResponseEntity<?> recoverPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado");
        }

        User user = userOpt.get();

        // Gerar e guardar token de recuperação
        String resetToken = UUID.randomUUID().toString();
        VerificationToken vt = VerificationToken.builder()
                .token(resetToken)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(30))
                .build();
        verificationTokenRepository.save(vt);

        // Link que abre a app +trust
        String resetLink = "http://localhost:8080/reset_password.html?token=" + resetToken;

        try {
            emailService.enviarEmailRecuperacao(email, resetLink);
            return ResponseEntity.ok("Email de recuperação enviado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar email.");
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String novaPassword = request.get("newPassword");

        Optional<VerificationToken> tokenOpt = verificationTokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido.");
        }

        VerificationToken tokenObj = tokenOpt.get();

        if (tokenObj.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expirado.");
        }

        User user = tokenObj.getUser();
        user.setPassword(passwordEncoder.encode(novaPassword));
        userRepository.save(user);

        verificationTokenRepository.delete(tokenObj); // remove token usado

        return ResponseEntity.ok("Password atualizada com sucesso.");
    }





    private boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\S]{8,}$");
    }
}
