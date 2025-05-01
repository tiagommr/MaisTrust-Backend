package com.trust.auth.repository;

import com.trust.auth.model.informacaoAdicionalSinistro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface informacaoAdicionalSinistroRepositorio extends JpaRepository<informacaoAdicionalSinistro, Long> {
    Optional<informacaoAdicionalSinistro> findById(Long id);
}
