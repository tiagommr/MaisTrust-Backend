package com.trust.auth.controller;

import com.trust.auth.model.documentoSinistro;
import com.trust.auth.repository.documentoSinistroRepositorio;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentos")
public class documentoSinistroControlador {

    private final documentoSinistroRepositorio repository;

    public documentoSinistroControlador(documentoSinistroRepositorio repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<documentoSinistro> listarTodos() {
        return repository.findAll();
    }
}
