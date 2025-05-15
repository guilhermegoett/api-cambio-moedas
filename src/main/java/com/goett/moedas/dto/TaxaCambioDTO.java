package com.goett.moedas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaxaCambioDTO {
    private Long id;
    private String moedaOrigem;
    private String moedaDestino;
    private String produto;
    private BigDecimal taxa;
    private LocalDateTime dataAtualizacao;
}