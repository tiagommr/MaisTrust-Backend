package com.trust.auth.repository;

import com.trust.auth.model.pedidoReembolso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface pedidoReembolsoRepositorio extends JpaRepository<pedidoReembolso, Long> {
    Optional<pedidoReembolso> findById(Long id);
}
