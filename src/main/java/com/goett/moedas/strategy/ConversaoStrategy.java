package com.goett.moedas.strategy;

import java.math.BigDecimal;

public interface ConversaoStrategy {
    BigDecimal converter(BigDecimal valorOriginal, BigDecimal taxa);
}
