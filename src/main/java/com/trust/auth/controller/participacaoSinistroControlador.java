package com.trust.auth.controller;

import com.trust.auth.model.participacaoSinistro;
import com.trust.auth.repository.participacaoSinistroRepositorio;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participacoes")
public class participacaoSinistroControlador {

    private final participacaoSinistroRepositorio repository;

    public participacaoSinistroControlador(participacaoSinistroRepositorio repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<participacaoSinistro> listarTodas() {
        return repository.findAll();
    }
}
