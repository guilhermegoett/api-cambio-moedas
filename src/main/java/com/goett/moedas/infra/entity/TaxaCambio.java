package com.goett.moedas.infra.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "taxa_cambio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxaCambio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Moeda moedaOrigem;

    @ManyToOne
    private Moeda moedaDestino;

    @ManyToOne
    private Produto produto;

    @Column(nullable = false)
    private BigDecimal taxa;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDate dataAtualizacao;
}
