package com.trust.auth.repository;

import com.trust.auth.model.documentoReembolso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface documentoReembolsoRepositorio extends JpaRepository<documentoReembolso, Long> {
    Optional<documentoReembolso> findById(Long id);
}
