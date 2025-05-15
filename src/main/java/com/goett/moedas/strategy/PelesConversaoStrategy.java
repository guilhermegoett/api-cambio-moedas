package com.goett.moedas.strategy;

import java.math.BigDecimal;

public class PelesConversaoStrategy implements ConversaoStrategy {
    @Override
    public BigDecimal converter(BigDecimal valorOriginal, BigDecimal taxa) {
        return valorOriginal.multiply(taxa).add(new BigDecimal("10.0")); // Exemplo: taxa + custo fixo
    }
}
