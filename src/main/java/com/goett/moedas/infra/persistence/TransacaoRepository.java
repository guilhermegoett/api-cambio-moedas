package com.goett.moedas.infra.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.goett.moedas.infra.entity.TransacaoEntity;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Long> {

    @Query("""
        SELECT t FROM TransacaoEntity t
        WHERE (:moedaOrigem IS NULL OR t.moedaOrigem.nome = :moedaOrigem)
          AND (:moedaDestino IS NULL OR t.moedaDestino.nome = :moedaDestino)
          AND (:reino IS NULL OR t.reino.nome = :reino)
          AND (:inicio IS NULL OR t.dataHora >= :inicio)
          AND (:fim IS NULL OR t.dataHora <= :fim)
    """)
    List<TransacaoEntity> buscarComFiltros(
        @Param("moedaOrigem") String moedaOrigem,
        @Param("moedaDestino") String moedaDestino,
        @Param("reino") String reino,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );
}
