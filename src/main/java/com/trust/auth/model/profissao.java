package com.trust.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profissao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class profissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profissao")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;
}
