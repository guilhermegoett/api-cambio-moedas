package com.goett.moedas.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reino")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReinoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "moeda_id", referencedColumnName = "id")
    private MoedaEntity moeda;
}
