package com.trust.auth.controller;

import com.trust.auth.model.partesCorpoLesionadas;
import com.trust.auth.repository.partesCorpoLesionadasRepositorio;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesoes")
public class partesCorpoLesionadasControlador {

    private final partesCorpoLesionadasRepositorio repository;

    public partesCorpoLesionadasControlador(partesCorpoLesionadasRepositorio repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<partesCorpoLesionadas> listarTodas() {
        return repository.findAll();
    }
}
