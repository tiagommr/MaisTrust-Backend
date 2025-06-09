// ✅ Atualizado clubeControlador.java para usar @AuthenticationPrincipal
package com.trust.auth.controller;

import com.trust.auth.model.atleta;
import com.trust.auth.model.clube;
import com.trust.auth.model.User;
import com.trust.auth.repository.AtletaRepositorio;
import com.trust.auth.repository.clubeRepositorio;
import com.trust.auth.dto.ClubeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clube")
public class clubeControlador {

    private final clubeRepositorio clubeRepositorio;
    private final AtletaRepositorio atletaRepositorio;

    public clubeControlador(clubeRepositorio clubeRepositorio, AtletaRepositorio atletaRepositorio) {
        this.clubeRepositorio = clubeRepositorio;
        this.atletaRepositorio = atletaRepositorio;
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

    // ✅ NOVO endpoint autenticado via token JWT
    @GetMapping("/nome")
    public ResponseEntity<String> getNomeClube(@AuthenticationPrincipal User user) {
        Optional<atleta> atletaOpt = atletaRepositorio.findByUser(user);
        if (atletaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atleta não encontrado");
        }

        clube clubeDoAtleta = atletaOpt.get().getClube();
        if (clubeDoAtleta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clube não encontrado");
        }

        return ResponseEntity.ok(clubeDoAtleta.getNome());
    }
}
