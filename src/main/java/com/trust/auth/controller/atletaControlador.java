package com.trust.auth.controller;

import com.trust.auth.model.atleta;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atletas")
public class atletaControlador {

    private final com.trust.auth.repository.atletaRepositorio atletaRepositorio;

    public atletaControlador(com.trust.auth.repository.atletaRepositorio atletaRepositorio) {
        this.atletaRepositorio = atletaRepositorio;
    }

    @GetMapping
    public List<atleta> listarTodos() {
        return atletaRepositorio.findAll();
    }
}
