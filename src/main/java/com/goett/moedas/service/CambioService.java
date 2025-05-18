package com.goett.moedas.service;

import com.goett.model.ConversaoRequest;
import com.goett.model.ConversaoResponse;
import com.goett.moedas.infra.entity.MoedaEntity;
import com.goett.moedas.infra.entity.ProdutoEntity;
import com.goett.moedas.infra.entity.ReinoEntity;
import com.goett.moedas.infra.entity.TaxaCambioEntity;
import com.goett.moedas.infra.entity.TransacaoEntity;
import com.goett.moedas.infra.persistence.MoedaRepository;
import com.goett.moedas.infra.persistence.ProdutoRepository;
import com.goett.moedas.infra.persistence.ReinoRepository;
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
    private final ProdutoRepository produtoRepository;
    private final TransacaoRepository transacaoRepository;
    private final ReinoRepository reinoRepository;
    private final MoedaRepository moedaRepository;

    public CambioService(ConversaoStrategyRegistry strategyRegistry, TaxaCambioRepository taxaCambioRepository, TransacaoRepository transacaoRepository, ProdutoRepository produtoRepository, ReinoRepository reinoRepository, MoedaRepository moedaRepository) {
        this.strategyRegistry = strategyRegistry;
        this.taxaCambioRepository = taxaCambioRepository;
        this.produtoRepository = produtoRepository;
        this.transacaoRepository = transacaoRepository;
        this.reinoRepository = reinoRepository;
        this.moedaRepository = moedaRepository;
    }

    public Optional<TaxaCambioEntity> obterTaxa(String moedaOrigem, String moedaDestino,  String produto) {

        Optional<TaxaCambioEntity> taxaOpt = Optional.ofNullable(taxaCambioRepository
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

    public boolean salvarTransacao(ConversaoRequest request, TaxaCambioEntity taxa, ConversaoResponse response) {
        Optional<ProdutoEntity> produto = produtoRepository.findByNome(request.getProduto());
        Optional<ReinoEntity> reino = reinoRepository.findByNome("SRM");
        Optional<MoedaEntity> moedaOrigem = moedaRepository.findByNome(request.getMoedaOrigem());
        Optional<MoedaEntity> moedaDestino = moedaRepository.findByNome(request.getMoedaDestino());

        try {
            TransacaoEntity transacao = new TransacaoEntity();
            transacao.setMoedaOrigem(moedaOrigem.get());
            transacao.setMoedaDestino(moedaDestino.get());
            transacao.setProduto(produto.get());
            transacao.setValorInicial(BigDecimal.valueOf(request.getValor()));
            transacao.setReino(reino.get());
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
