package com.trust.auth.repository;

import com.trust.auth.model.clube;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface clubeRepositorio extends JpaRepository<clube, Long> {

    Optional<clube> findByNif(String nif);

    List<clube> findByFederacaoId(Long federacaoId);

}
