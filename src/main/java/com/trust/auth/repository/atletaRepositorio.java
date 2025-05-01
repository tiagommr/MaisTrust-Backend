package com.trust.auth.repository;

import com.trust.auth.model.atleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface atletaRepositorio extends JpaRepository<atleta, Long> {
    Optional<atleta> findByNif(String nif);
}
