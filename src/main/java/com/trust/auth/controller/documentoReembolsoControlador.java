package com.trust.auth.controller;

import com.trust.auth.model.documentoReembolso;
import com.trust.auth.repository.documentoReembolsoRepositorio;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentos-reembolso")
public class documentoReembolsoControlador {

    private final documentoReembolsoRepositorio repository;

    public documentoReembolsoControlador(documentoReembolsoRepositorio repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<documentoReembolso> listarTodos() {
        return repository.findAll();
    }
}
