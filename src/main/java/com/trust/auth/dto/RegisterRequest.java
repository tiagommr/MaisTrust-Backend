package com.trust.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nome;
    private String email;
    private String password;
    private String telefone;
    private String dataNascimento;
    private String tipoUtilizador;
    private Long idClube;
    private Long idFederacao;
}
