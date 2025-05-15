package com.goett.moedas.adapter.controller;

import com.goett.moedas.CambioApi; // interface gerada pelo OpenAPI Generator
import com.goett.moedas.exception.TransacaoNaoSalvaException;
import com.goett.moedas.infra.entity.TaxaCambio;
import com.goett.model.ConversaoRequest;
import com.goett.model.ConversaoResponse;
import com.goett.model.Moeda;
import com.goett.model.Produto;
import com.goett.model.TaxaCambioResponse;
import com.goett.moedas.service.CambioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
public class ControllerApiCambio implements CambioApi {

    private final CambioService cambioService;

    public ControllerApiCambio(CambioService cambioService) {
        this.cambioService = cambioService;
    }

    @Override
    public ResponseEntity<TaxaCambioResponse> cambioGet(Moeda moedaOrigem, Moeda moedaDestino, Produto produto) {
        TaxaCambio taxa = cambioService.obterTaxa(moedaOrigem.getValue(), moedaDestino.getValue(), produto.getValue()).get();
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
        TaxaCambio taxa = cambioService
                .obterTaxa(request.getMoedaOrigem().getValue(), request.getMoedaDestino().getValue(), request.getProduto().getValue()).get();

        ConversaoResponse response = cambioService.converterMoeda(request, taxa.getTaxa());
        boolean transacaoSalva = cambioService.salvarTransacao(request, taxa, response);
        if (!transacaoSalva) {
            throw new TransacaoNaoSalvaException("Erro ao salvar a transação de câmbio.");
        }
        return ResponseEntity.ok(response);
    }
}
