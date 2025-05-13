package com.trust.auth.dto;

public class UserResponse {
    private Long id;
    private String nome;
    private String email;
    private String tipoUtilizador;
    private String telefone;

    public UserResponse(Long id, String nome, String email, String tipoUtilizador, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipoUtilizador = tipoUtilizador;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoUtilizador() {
        return tipoUtilizador;
    }

    public void setTipoUtilizador(String tipoUtilizador) {
        this.tipoUtilizador = tipoUtilizador;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
