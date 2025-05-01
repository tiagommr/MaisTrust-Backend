package com.trust.auth.controller;

import com.trust.auth.model.informacaoAdicionalSinistro;
import com.trust.auth.repository.informacaoAdicionalSinistroRepositorio;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/info-adicional")
public class informacaoAdicionalSinistroControlador {

    private final informacaoAdicionalSinistroRepositorio repository;

    public informacaoAdicionalSinistroControlador(informacaoAdicionalSinistroRepositorio repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<informacaoAdicionalSinistro> listarTodas() {
        return repository.findAll();
    }
}
