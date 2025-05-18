package com.goett.moedas.infra.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.goett.moedas.infra.entity.TaxaCambioEntity;

@Repository
public interface TaxaCambioRepository extends JpaRepository<TaxaCambioEntity, Long> {
    List<TaxaCambioEntity> findByMoedaOrigemNomeAndMoedaDestinoNomeAndProdutoNome(String moedaOrigem, String moedaDestino, String produto);

    List<TaxaCambioEntity> findByMoedaOrigemNomeOrMoedaDestinoNomeOrProdutoNomeOrDataAtualizacaoBetween(
        String moedaOrigem, String moedaDestino, String produto, LocalDateTime start, LocalDateTime end);
}