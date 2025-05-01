package com.trust.auth.repository;

import com.trust.auth.model.documentoSinistro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface documentoSinistroRepositorio extends JpaRepository<documentoSinistro, Long> {

    Optional<documentoSinistro> findById(Long id);
}
