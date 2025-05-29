package com.trust.auth.controller;

import com.trust.auth.model.clube;
import com.trust.auth.repository.clubeRepositorio;
import org.springframework.web.bind.annotation.*;
import com.trust.auth.dto.ClubeDTO;

import java.util.List;

@RestController
@RequestMapping("/clube")
public class clubeControlador {

    private final clubeRepositorio clubeRepositorio;

    public clubeControlador(clubeRepositorio clubeRepositorio) {
        this.clubeRepositorio = clubeRepositorio;
    }

    @GetMapping
    public List<clube> getAllClube() {
        return clubeRepositorio.findAll();
    }

    @GetMapping("/por-federacao/{id}")
    public List<clube> getClubesPorFederacao(@PathVariable Long id) {
        return clubeRepositorio.findByFederacaoId(id);
    }

    @GetMapping("/todos")
    public List<ClubeDTO> listarTodosOsClubes() {
        return clubeRepositorio.findAll().stream()
                .map(c -> new ClubeDTO(c.getId(), c.getNome()))
                .toList();
    }

}
