package com.trust.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documento_sinistro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class documentoSinistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documentosi")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sinistro", referencedColumnName = "id_sinistro")
    private participacaoSinistro participacaoSinistro;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "caminho_arquivo")
    private String caminhoArquivo;
}
