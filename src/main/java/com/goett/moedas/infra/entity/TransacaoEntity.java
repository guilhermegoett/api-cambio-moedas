package com.goett.moedas.infra.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@Table(name = "transacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProdutoEntity produto;

    @Column(nullable = false)
    private BigDecimal valorInicial;

    @Column(nullable = false)
    private BigDecimal valorFinal;

    @Column(nullable = false)
    private BigDecimal taxa;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne
    private ReinoEntity reino;

    @ManyToOne
    private MoedaEntity moedaOrigem;

    @ManyToOne
    private MoedaEntity moedaDestino;
}
