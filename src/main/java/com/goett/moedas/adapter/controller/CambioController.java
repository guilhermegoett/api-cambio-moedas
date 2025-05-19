package com.goett.moedas.adapter.controller;

import com.goett.moedas.CambioApi; // interface gerada pelo OpenAPI Generator
import com.goett.moedas.exception.TransacaoNaoSalvaException;
import com.goett.moedas.infra.entity.TaxaCambioEntity;
import com.goett.model.ConversaoRequest;
import com.goett.model.ConversaoResponse;
import com.goett.model.TaxaCambioResponse;
import com.goett.moedas.service.CambioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
public class CambioController implements CambioApi {

    private final CambioService cambioService;

    public CambioController(CambioService cambioService) {
        this.cambioService = cambioService;
    }

    @Override
    public ResponseEntity<TaxaCambioResponse> cambioGet(String moedaOrigem, String moedaDestino, String produto) {
        TaxaCambioEntity taxa = cambioService.obterTaxa(moedaOrigem, moedaDestino, produto).get();
        LocalDateTime datahorLocalDateTime = taxa.getDataAtualizacao().atStartOfDay();
        TaxaCambioResponse response = new TaxaCambioResponse()
                .moedaOrigem(moedaOrigem)
                .moedaDestino(moedaDestino)
                .produto(produto)
                .taxa(taxa.getTaxa().doubleValue())
                .dataAtualizacao(datahorLocalDateTime.atOffset(ZoneOffset.UTC));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ConversaoResponse> cambioConversaoPost(ConversaoRequest request) {
        TaxaCambioEntity taxa = cambioService
                .obterTaxa(request.getMoedaOrigem(), request.getMoedaDestino(), request.getProduto()).get();

        ConversaoResponse response = cambioService.converterMoeda(request, taxa.getTaxa());
        boolean transacaoSalva = cambioService.salvarTransacao(request, taxa, response);
        if (!transacaoSalva) {
            throw new TransacaoNaoSalvaException("Erro ao salvar a transação de câmbio.");
        }
        return ResponseEntity.ok(response);
    }
}
