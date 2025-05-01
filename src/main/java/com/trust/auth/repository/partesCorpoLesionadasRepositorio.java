package com.trust.auth.repository;

import com.trust.auth.model.partesCorpoLesionadas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface partesCorpoLesionadasRepositorio extends JpaRepository<partesCorpoLesionadas, Long> {
    Optional<partesCorpoLesionadas> findById(Long id);
}
