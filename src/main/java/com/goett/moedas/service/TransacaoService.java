package com.goett.moedas.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.goett.moedas.infra.entity.TransacaoEntity;
import com.goett.moedas.infra.persistence.TransacaoRepository;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    /**
     * Busca transações com filtros opcionais (null ignora o filtro).
     */
    public List<TransacaoEntity> buscarComFiltros(
            String moedaOrigem, String moedaDestino, String reino, LocalDateTime inicio, LocalDateTime fim) {
        return transacaoRepository.buscarComFiltros(moedaOrigem, moedaDestino, reino, inicio, fim);
    }
}
