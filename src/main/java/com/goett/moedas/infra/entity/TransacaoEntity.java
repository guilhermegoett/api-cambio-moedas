package com.goett.moedas.infra.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "produto_id")
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
    @JoinColumn(name = "reino_id")
    private ReinoEntity reino;

    @ManyToOne
    @JoinColumn(name = "moeda_origem_id")
    private MoedaEntity moedaOrigem;

    @ManyToOne
    @JoinColumn(name = "moeda_destino_id")
    private MoedaEntity moedaDestino;
}
