package com.trust.auth.controller;

import com.trust.auth.model.clube;
import com.trust.auth.repository.clubeRepositorio;
import org.springframework.web.bind.annotation.*;

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


}
