package com.goett.moedas.adapter.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.goett.moedas.service.TransacaoService;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.goett.moedas.infra.entity.TransacaoEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
@Tag(name = "Transacoes", description = "Operações relacionadas aos comércio entre reinos do mundo fantástico")
public class TransacaoController {

    private final TransacaoService transacaoService;

    /**
     * Busca transações com filtros opcionais.
     */
    @GetMapping("/filtrados")
    public ResponseEntity<List<TransacaoEntity>> buscarComFiltros(
            @RequestParam(required = false) String moedaOrigem,
            @RequestParam(required = false) String moedaDestino,
            @RequestParam(required = false) String reino,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        List<TransacaoEntity> transacoes = transacaoService.buscarComFiltros(
                moedaOrigem, moedaDestino, reino, inicio, fim);
        return ResponseEntity.ok(transacoes);
    }
}
