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
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("Email recebido: " + request.getEmail());
        System.out.println("Password recebida: " + request.getPassword());

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println("Utilizador encontrado na BD: " + user.getEmail());
            System.out.println("Password na BD: " + user.getPassword());

            if (user.getPassword().equals(request.getPassword())) {
                System.out.println("Password correta!");
                user.setPassword(null);
                return ResponseEntity.ok(user);
            } else {
                System.out.println("Password incorreta.");
            }
        } else {
            System.out.println("Utilizador não encontrado.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

}
