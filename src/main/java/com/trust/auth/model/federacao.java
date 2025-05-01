package com.trust.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "federacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class federacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_federacao")
    private Long id;

    private String nome;

    @Column(unique = true)
    private String nif;

    private String morada;

    private String telefone;
}
