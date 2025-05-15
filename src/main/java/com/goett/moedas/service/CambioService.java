package com.goett.moedas.service;

import com.goett.model.ConversaoRequest;
import com.goett.model.ConversaoResponse;
import com.goett.moedas.infra.entity.TaxaCambio;
import com.goett.moedas.infra.entity.Transacao;
import com.goett.moedas.infra.persistence.TaxaCambioRepository;
import com.goett.moedas.infra.persistence.TransacaoRepository;
import com.goett.moedas.strategy.ConversaoStrategyRegistry;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CambioService {

    private final ConversaoStrategyRegistry strategyRegistry;
    private final TaxaCambioRepository taxaCambioRepository;
    private final TransacaoRepository transacaoRepository;

    public CambioService(ConversaoStrategyRegistry strategyRegistry, TaxaCambioRepository taxaCambioRepository, TransacaoRepository transacaoRepository) {
        this.strategyRegistry = strategyRegistry;
        this.taxaCambioRepository = taxaCambioRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public Optional<TaxaCambio> obterTaxa(String moedaOrigem, String moedaDestino,  String produto) {

        Optional<TaxaCambio> taxaOpt = Optional.ofNullable(taxaCambioRepository
                .findByMoedaOrigemNomeAndMoedaDestinoNomeAndProdutoNome(
                        moedaOrigem, moedaDestino, produto)
                .stream().findFirst().orElseThrow(() -> new NoSuchElementException("Taxa de câmbio não encontrada")));

        return taxaOpt;
    }

    public ConversaoResponse converterMoeda(ConversaoRequest request, BigDecimal taxa) {
        BigDecimal valorOriginal = BigDecimal.valueOf(request.getValor());
        BigDecimal valorConvertido = strategyRegistry.getStrategy(request.getProduto())
                .converter(valorOriginal, taxa);

        return new ConversaoResponse()
                .moedaOrigem(request.getMoedaOrigem())
                .moedaDestino(request.getMoedaDestino())
                .produto(request.getProduto())
                .valorConvertido(valorConvertido.doubleValue())
                .taxaAplicada(taxa.doubleValue())
                .dataHora(OffsetDateTime.now());
    }

    public boolean salvarTransacao(ConversaoRequest request, TaxaCambio taxa, ConversaoResponse response) {
        try {
            Transacao transacao = new Transacao();
            transacao.setMoedaOrigem(request.getMoedaOrigem().getValue());
            transacao.setMoedaDestino(request.getMoedaDestino().getValue());
            transacao.setProduto(request.getProduto().getValue());
            transacao.setValorInicial(BigDecimal.valueOf(request.getValor()));
            transacao.setReino("SRM");
            transacao.setValorFinal(BigDecimal.valueOf(response.getValorConvertido()));
            transacao.setTaxa(taxa.getTaxa());
            transacao.setDataHora(LocalDateTime.now());

            transacaoRepository.save(transacao);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
   
}
