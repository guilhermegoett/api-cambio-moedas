package com.goett.moedas.strategy;

import com.goett.model.Produto;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class ConversaoStrategyRegistry {

    private final Map<Produto, ConversaoStrategy> strategies = new EnumMap<>(Produto.class);

    public ConversaoStrategyRegistry() {
        strategies.put(Produto.PELES, new PelesConversaoStrategy());
        strategies.put(Produto.MADEIRA, new MadeiraConversaoStrategy());
        strategies.put(Produto.HIDROMEL, (valor, taxa) -> valor.multiply(taxa)); // lambda simples
    }

    public ConversaoStrategy getStrategy(Produto produto) {
        return strategies.getOrDefault(produto, (valor, taxa) -> valor.multiply(taxa)); // default
    }
}
