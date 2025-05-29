package com.trust.auth.repository;

import com.trust.auth.model.User;
import com.trust.auth.model.atleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtletaRepositorio extends JpaRepository<atleta, Long> {

    // Buscar atleta pelo NIF (já existente no teu projeto)
    Optional<atleta> findByNif(String nif);

    // Novo método para buscar atleta pelo utilizador autenticado
    Optional<atleta> findByUser(User user);
}
