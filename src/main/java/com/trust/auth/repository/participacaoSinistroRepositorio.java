package com.trust.auth.repository;

import com.trust.auth.model.participacaoSinistro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface participacaoSinistroRepositorio extends JpaRepository<participacaoSinistro, Long> {

    Optional<participacaoSinistro> findById(Long id);

    participacaoSinistro findByIdAtletaAndIdClubeAndIdFederacaoAndEstadoAndValidador(
            Long idAtleta, Long idClube, Long idFederacao, String estado, Long validador
    );

    participacaoSinistro findByIdAtletaAndIdClubeAndIdFederacaoAndEstado(
            Long idAtleta, Long idClube, Long idFederacao, String estado
    );
}
