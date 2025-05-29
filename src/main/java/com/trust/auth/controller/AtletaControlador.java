package com.trust.auth.controller;

import com.trust.auth.model.User;
import com.trust.auth.model.atleta;
import com.trust.auth.model.clube;
import com.trust.auth.model.federacao;
import com.trust.auth.dto.AtletaRequest;
import com.trust.auth.repository.AtletaRepositorio;
import com.trust.auth.repository.clubeRepositorio;
import com.trust.auth.repository.federacaoRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/atletas")
public class AtletaControlador {

    private final AtletaRepositorio atletaRepositorio;
    private final clubeRepositorio clubeRepositorio;
    private final federacaoRepositorio federacaoRepositorio;

    public AtletaControlador(AtletaRepositorio atletaRepositorio, clubeRepositorio clubeRepositorio, federacaoRepositorio federacaoRepositorio) {
        this.atletaRepositorio = atletaRepositorio;
        this.clubeRepositorio = clubeRepositorio;
        this.federacaoRepositorio = federacaoRepositorio;
    }

    @GetMapping
    public List<atleta> listarTodos() {
        return atletaRepositorio.findAll();
    }

    @GetMapping("/verificar-incompleto")
    public ResponseEntity<Boolean> verificarDadosIncompletos(@AuthenticationPrincipal User user) {
        Optional<atleta> optionalAtleta = atletaRepositorio.findByUser(user);

        if (optionalAtleta.isEmpty()) return ResponseEntity.ok(true);

        atleta a = optionalAtleta.get();

        boolean incompleto = Stream.of(
                a.getDataNascimento(),
                a.getEstadoCivil(),
                a.getProfissao(),
                a.getTelefone(),
                a.getMorada(),
                a.getCodigoPostal(),
                a.getLocalidade(),
                a.getNif(),
                a.getLicenca(),
                a.getEscalao(),
                a.getCategoria(),
                a.getDataInicioCobertura(),
                a.getClube(),
                a.getFederacao()
        ).anyMatch(Objects::isNull);

        return ResponseEntity.ok(incompleto);
    }

    @PostMapping
    public ResponseEntity<?> criarAtleta(@RequestBody AtletaRequest request, @AuthenticationPrincipal User user) {
        Optional<clube> clubeOpt = clubeRepositorio.findById(request.getIdClube());
        Optional<federacao> federacaoOpt = federacaoRepositorio.findById(request.getIdFederacao());

        if (clubeOpt.isEmpty() || federacaoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Clube ou Federação inválidos");
        }

        atleta novo = atleta.builder()
                .user(user)
                .dataNascimento(request.getDataNascimento())
                .estadoCivil(request.getEstadoCivil())
                .profissao(request.getProfissao())
                .telefone(request.getTelefone())
                .morada(request.getMorada())
                .codigoPostal(request.getCodigoPostal())
                .localidade(request.getLocalidade())
                .nomeResponsavel(request.getNomeResponsavel())
                .relacaoResponsavel(request.getRelacaoResponsavel())
                .nif(request.getNif())
                .licenca(request.getLicenca())
                .escalao(request.getEscalao())
                .categoria(request.getCategoria())
                .dataInicioCobertura(LocalDate.now())
                .clube(clubeOpt.get())
                .federacao(federacaoOpt.get())
                .build();

        atletaRepositorio.save(novo);
        return ResponseEntity.ok("✅ Dados gravados com sucesso");
    }
}
