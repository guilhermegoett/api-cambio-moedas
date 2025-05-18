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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "taxa_cambio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxaCambioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MoedaEntity moedaOrigem;

    @ManyToOne
    private MoedaEntity moedaDestino;

    @ManyToOne
    private ProdutoEntity produto;

    @Column(nullable = false)
    private BigDecimal taxa;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDate dataAtualizacao;
}
