package com.goett.moedas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TransacaoDTO {
    private Long id;
    private String moedaOrigem;
    private String moedaDestino;
    private String produto;
    private BigDecimal valorInicial;
    private BigDecimal valorFinal;
    private LocalDateTime dataHora;
    private String reino;
}
