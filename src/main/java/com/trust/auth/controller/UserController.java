package com.trust.auth.controller;

import com.trust.auth.dto.UserResponse;
import com.trust.auth.model.User;
import com.trust.auth.model.atleta;
import com.trust.auth.repository.AtletaRepositorio;
import com.trust.auth.repository.UserRepository;
import com.trust.auth.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Optional;

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

    @Autowired
    private AtletaRepositorio atletaRepositorio;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String token = jwtService.getJwtFromRequest(request);
        if (token != null && jwtService.isTokenValid(token)) {
            Long userId = jwtService.getUserIdFromToken(token);
            User user = userRepository.findById(userId).orElse(null);

            if (user != null) {
                HashMap<Object, Object> response = new HashMap<>();
                response.put("id", user.getId());
                response.put("nome", user.getNome());
                response.put("email", user.getEmail());
                response.put("tipoUtilizador", user.getTipoUtilizador());
                response.put("telefone", user.getTelefone());

                // Procurar o atleta associado
                Optional<atleta> atletaOpt = atletaRepositorio.findByUser(user);
                if (atletaOpt.isPresent()) {
                    atleta a = atletaOpt.get();
                    if (a.getClube() != null) {
                        response.put("clube", a.getClube().getNome());
                    }
                    if (a.getFederacao() != null) {
                        response.put("federacao", a.getFederacao().getNome());
                    }
                }

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
