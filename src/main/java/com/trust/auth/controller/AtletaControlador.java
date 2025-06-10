package com.trust.auth.controller;

import com.trust.auth.model.User;
import com.trust.auth.model.atleta;
import com.trust.auth.model.profissao;
import com.trust.auth.model.clube;
import com.trust.auth.model.federacao;
import com.trust.auth.dto.AtletaRequest;
import com.trust.auth.repository.AtletaRepositorio;
import com.trust.auth.repository.profissaoRepositorio;
import com.trust.auth.repository.clubeRepositorio;
import com.trust.auth.repository.federacaoRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping("/atletas")
public class AtletaControlador {

    private final AtletaRepositorio atletaRepositorio;
    private final clubeRepositorio clubeRepositorio;
    private final federacaoRepositorio federacaoRepositorio;
    private final profissaoRepositorio profissaoRepositorio;

    public AtletaControlador(AtletaRepositorio atletaRepositorio, clubeRepositorio clubeRepositorio,
                             federacaoRepositorio federacaoRepositorio, profissaoRepositorio profissaoRepositorio) {
        this.atletaRepositorio = atletaRepositorio;
        this.clubeRepositorio = clubeRepositorio;
        this.federacaoRepositorio = federacaoRepositorio;
        this.profissaoRepositorio = profissaoRepositorio;
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

    @GetMapping("/me")
    public ResponseEntity<?> getDadosAtleta(@AuthenticationPrincipal User userAutenticado) {
        Optional<atleta> optionalAtleta = atletaRepositorio.findByUser(userAutenticado);

        if (optionalAtleta.isEmpty()) {
            return ResponseEntity.badRequest().body("Atleta não encontrado");
        }

        atleta atletaEncontrado = optionalAtleta.get();

        Map<String, Object> resposta = new HashMap<>();

        resposta.put("nome", userAutenticado.getNome());
        resposta.put("email", userAutenticado.getEmail());
        resposta.put("dataNascimento", atletaEncontrado.getDataNascimento());
        resposta.put("telefone", atletaEncontrado.getTelefone());
        resposta.put("morada", atletaEncontrado.getMorada());
        resposta.put("codigoPostal", atletaEncontrado.getCodigoPostal());
        resposta.put("localidade", atletaEncontrado.getLocalidade());
        resposta.put("nif", atletaEncontrado.getNif());
        resposta.put("nomeResponsavel", atletaEncontrado.getNomeResponsavel());
        resposta.put("relacaoResponsavel", atletaEncontrado.getRelacaoResponsavel());
        resposta.put("profissao", atletaEncontrado.getProfissao());

        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/me")
    public ResponseEntity<?> atualizarDadosAtleta(@RequestBody Map<String, Object> atualizados, @AuthenticationPrincipal User user) {
        Optional<atleta> optionalAtleta = atletaRepositorio.findByUser(user);

        if (optionalAtleta.isEmpty()) {
            return ResponseEntity.badRequest().body("Atleta não encontrado");
        }

        atleta a = optionalAtleta.get();

        a.setTelefone((String) atualizados.get("telefone"));
        a.setMorada((String) atualizados.get("morada"));
        a.setProfissao((String) atualizados.get("profissao"));
        a.setNif((String) atualizados.get("nif"));
        a.setNomeResponsavel((String) atualizados.get("nomeResponsavel"));
        a.setRelacaoResponsavel((String) atualizados.get("relacaoResponsavel"));

        atletaRepositorio.save(a);

        return ResponseEntity.ok("✅ Dados atualizados com sucesso");
    }

    @GetMapping("/profissoes")
    public ResponseEntity<List<Map<String, Object>>> listarProfissoes() {
        List<profissao> profissoes = profissaoRepositorio.findAll();

        List<Map<String, Object>> resultado = profissoes.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("nome", p.getNome());
            return map;
        }).toList();

        return ResponseEntity.ok(resultado);
    }
}
