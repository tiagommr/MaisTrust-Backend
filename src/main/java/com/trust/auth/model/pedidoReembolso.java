package com.trust.auth.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pedido_reembolso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class pedidoReembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reembolso")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sinistro", referencedColumnName = "id_sinistro")
    private participacaoSinistro participacaoSinistro;

    @Column(name = "data_solicitacao")
    private LocalDate dataSolicitacao;

    private String estado;
}
