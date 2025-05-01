package com.trust.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documento_reembolso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class documentoReembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documentore")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_reembolso", referencedColumnName = "id_reembolso")
    private pedidoReembolso pedidoReembolso;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "caminho_arquivo")
    private String caminhoArquivo;
}
