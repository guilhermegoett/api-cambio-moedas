package com.goett.moedas.infra.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.goett.moedas.infra.entity.TaxaCambio;

@Repository
public interface TaxaCambioRepository extends JpaRepository<TaxaCambio, Long> {
    List<TaxaCambio> findByMoedaOrigemNomeAndMoedaDestinoNomeAndProdutoNome(String moedaOrigem, String moedaDestino, String produto);

    List<TaxaCambio> findByMoedaOrigemNomeOrMoedaDestinoNomeOrProdutoNomeOrDataAtualizacaoBetween(
        String moedaOrigem, String moedaDestino, String produto, LocalDateTime start, LocalDateTime end);
}