package com.goett.moedas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReinoDTO {
    private Long id;
    private String nome;
    private Long moedaId;
}