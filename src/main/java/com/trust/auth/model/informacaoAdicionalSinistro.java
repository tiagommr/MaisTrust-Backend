package com.trust.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "informacao_adicional_sinistro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class informacaoAdicionalSinistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_info")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_sinistro", referencedColumnName = "id_sinistro")
    private participacaoSinistro participacaoSinistro;

    @Column(name = "precisou_assistencia")
    private Boolean precisouAssistencia;

    @Column(name = "primeiros_socorros")
    private Boolean primeirosSocorros;

    @Column(name = "quem_prestou")
    private String quemPrestou;

    @Column(name = "foi_reportado")
    private Boolean foiReportado;

    private String autoridade;

    @Column(name = "numero_processo")
    private String numeroProcesso;

    private String testemunhas;

    @Column(name = "ficou_hospitalizado")
    private Boolean ficouHospitalizado;

    @Column(name = "ficou_incapacitado")
    private Boolean ficouIncapacitado;

    @Column(name = "tem_seguro")
    private Boolean temSeguro;
}
