package com.goett.moedas.infra.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Produto;

    @Column(name = "valor_inicial", nullable = false)
    private BigDecimal valorInicial;

    @Column(name = "valor_final", nullable = false)
    private BigDecimal valorFinal;

    @Column(nullable = false)
    private BigDecimal taxa;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "reino")
    private String reino;

    @Column(name = "moeda_origem")
    private String moedaOrigem;

    @Column(name = "moeda_destino")
    private String moedaDestino;
}
