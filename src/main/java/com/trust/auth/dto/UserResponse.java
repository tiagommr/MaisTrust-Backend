package com.trust.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String nome;
    private String email;
    private String tipoUtilizador;
}
