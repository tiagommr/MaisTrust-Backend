package com.trust.auth.controller;

import com.trust.auth.model.pedidoReembolso;
import com.trust.auth.repository.pedidoReembolsoRepositorio;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reembolsos")
public class pedidoReembolsoControlador {

    private final pedidoReembolsoRepositorio repository;

    public pedidoReembolsoControlador(pedidoReembolsoRepositorio repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<pedidoReembolso> listarTodos() {
        return repository.findAll();
    }
}
