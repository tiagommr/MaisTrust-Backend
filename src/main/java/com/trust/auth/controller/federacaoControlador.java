package com.trust.auth.controller;

import com.trust.auth.model.federacao;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/federacoes")
public class federacaoControlador {

    private final com.trust.auth.repository.federacaoRepositorio federacaoRepositorio;

    public federacaoControlador(com.trust.auth.repository.federacaoRepositorio federacaoRepositorio) {
        this.federacaoRepositorio = federacaoRepositorio;
    }

    @GetMapping
    public List<federacao> listarTodas() {
        return federacaoRepositorio.findAll();
    }
}
