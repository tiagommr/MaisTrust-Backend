package com.trust.auth.repository;

import com.trust.auth.model.participacaoSinistro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface participacaoSinistroRepositorio extends JpaRepository<participacaoSinistro, Long> {

    Optional<participacaoSinistro> findById(Long id);

    participacaoSinistro findByAtletaIdAndClubeIdAndFederacaoIdAndEstadoAndValidadorId(
            Long atletaId, Long clubeId, Long federacaoId, String estado, Long validadorId
    );

    participacaoSinistro findByAtletaIdAndClubeIdAndFederacaoIdAndEstado(
            Long atletaId, Long clubeId, Long federacaoId, String estado
    );
}

