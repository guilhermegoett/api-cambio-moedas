package com.goett.moedas.infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goett.moedas.infra.entity.ReinoEntity;

@Repository
public interface ReinoRepository extends JpaRepository<ReinoEntity, Long> {
    Optional<ReinoEntity> findByNome(String nome);
}  