package com.goett.moedas.strategy;

import java.math.BigDecimal;

public class MadeiraConversaoStrategy implements ConversaoStrategy {
    @Override
    public BigDecimal converter(BigDecimal valorOriginal, BigDecimal taxa) {
        return valorOriginal.multiply(taxa).multiply(new BigDecimal("0.95")); // Exemplo: desconto de 5%
    }
}
