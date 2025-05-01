package com.trust.auth.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "participacao_sinistro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class participacaoSinistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sinistro")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_atleta", referencedColumnName = "id_atleta")
    private com.trust.auth.model.atleta atleta;

    @ManyToOne
    @JoinColumn(name = "id_clube", referencedColumnName = "id_clube")
    private com.trust.auth.model.clube clube;

    @ManyToOne
    @JoinColumn(name = "id_federacao", referencedColumnName = "id_federacao")
    private com.trust.auth.model.federacao federacao;

    @ManyToOne
    @JoinColumn(name = "id_validador", referencedColumnName = "id")
    private User validador;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    private String estado;

    @Column(name = "motivo_recusa")
    private String motivoRecusa;

    @Column(name = "codigo_validacao")
    private String codigoValidacao;

    @Column(name = "data_acidente")
    private LocalDate dataAcidente;

    @Column(name = "hora_acidente")
    private LocalTime horaAcidente;

    @Column(name = "local_acidente")
    private String localAcidente;

    private String descricao;
}
