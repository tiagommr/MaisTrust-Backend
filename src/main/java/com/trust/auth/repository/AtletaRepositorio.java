package com.trust.auth.repository;

import com.trust.auth.model.User;
import com.trust.auth.model.atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AtletaRepositorio extends JpaRepository<atleta, Long> {

    // Novo método para buscar atleta pelo utilizador autenticado
    Optional<atleta> findByUser(User user);

    @Query("SELECT a FROM atleta a WHERE a.user.id = :userId")
    Optional<atleta> findByUserId(@Param("userId") Long userId);

}
