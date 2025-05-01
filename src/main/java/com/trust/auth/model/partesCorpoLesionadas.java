package com.trust.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "partes_corpo_lesionadas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class partesCorpoLesionadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lesao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sinistro", referencedColumnName = "id_sinistro")
    private participacaoSinistro participacaoSinistro;

    @Column(name = "parte_corpo")
    private String parteCorpo;
}
