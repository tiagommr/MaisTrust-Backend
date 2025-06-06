package com.trust.auth.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "atleta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class atleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atleta")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_utilizador", referencedColumnName = "id")
    private User user;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    private String telefone;
    private String morada;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    private String localidade;

    @Column(name = "nome_responsavel")
    private String nomeResponsavel;

    @Column(name = "relacao_responsavel")
    private String relacaoResponsavel;

    @Column(unique = true)
    private String nif;

    private String licenca;
    private String escalao;
    private String categoria;

    @Column(name = "data_inicio_cobertura")
    private LocalDate dataInicioCobertura;

    @ManyToOne
    @JoinColumn(name = "id_clube")
    private clube clube;

    @ManyToOne
    @JoinColumn(name = "id_federacao")
    private federacao federacao;

    private String profissao;




}

