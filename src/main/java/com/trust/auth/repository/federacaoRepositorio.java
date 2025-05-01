package com.trust.auth.repository;

import com.trust.auth.model.federacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface federacaoRepositorio extends JpaRepository<federacao, Long> {
    // Custom query methods can be defined here if needed
    // For example, to find a federacao by its name:

    Optional<federacao> findByNome(String nome);

}
