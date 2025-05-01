package com.trust.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clube")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class clube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clube")
    private Long id;

    private String nome;

    @Column(unique = true)
    private String nif;

    private String morada;

    @Column(unique = true)
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "id_federacao", referencedColumnName = "id_federacao")
    private com.trust.auth.model.federacao federacao;
}
