package com.goett.moedas.exception;

public class ProdutoJaExisteException extends RuntimeException {
    public ProdutoJaExisteException(String nome) {
        super("Produto com nome '" + nome + "' já existe.");
    }
}
