package com.trust.auth.controller;

import com.trust.auth.dto.LoginRequest;
import com.trust.auth.dto.RegisterRequest;
import com.trust.auth.model.User;
import com.trust.auth.security.JwtService;  // Importando o JwtService para geração de token
import com.trust.auth.repository.UserRepository;
import com.trust.auth.security.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

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
                // Gera o token JWT
                String token = jwtService.generateToken(user.getId());

                System.out.println("Token gerado: " + token);  // Adicionando log para ver o token gerado

                // Cria um objeto de resposta com os dados do usuário e o token
                user.setPassword(null); // Não retornar a senha
                return ResponseEntity.ok(new LoginResponse(user, token)); // Envia o usuário e token na resposta
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }



    private boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\S]{8,}$");
    }
}
