package com.goett.moedas.infra.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.goett.moedas.infra.entity.MoedaEntity;

@Repository
public interface MoedaRepository extends JpaRepository<MoedaEntity, Long> {
    Optional<MoedaEntity> findByNome(String nome);
}