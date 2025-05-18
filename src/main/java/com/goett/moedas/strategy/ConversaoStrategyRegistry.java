package com.goett.moedas.strategy;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConversaoStrategyRegistry {

    private final Map<String, ConversaoStrategy> strategies = new HashMap<>();

    public ConversaoStrategyRegistry() {
        strategies.put("PELES", new PelesConversaoStrategy());
        strategies.put("MADEIRA", new MadeiraConversaoStrategy());
        strategies.put("HIDROMEL", (valor, taxa) -> valor.multiply(taxa)); // lambda simples
    }

    public ConversaoStrategy getStrategy(String produto) {
        return strategies.getOrDefault(produto.toUpperCase(), (valor, taxa) -> valor.multiply(taxa));
    }
}
