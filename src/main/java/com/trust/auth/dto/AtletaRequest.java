package com.trust.auth.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AtletaRequest {

    private Long idClube;
    private Long idFederacao;

    private LocalDate dataNascimento;
    private String estadoCivil;
    private String profissao;
    private String telefone;
    private String morada;
    private String codigoPostal;
    private String localidade;

    private String nomeResponsavel;
    private String relacaoResponsavel;

    private String nif;
    private String licenca;
    private String escalao;
    private String categoria;
    private LocalDate dataInicioCobertura;

}
