package com.goett.moedas.exception;

public class TransacaoNaoSalvaException extends RuntimeException {
    public TransacaoNaoSalvaException(String mensagem) {
        super(mensagem);
    }
}
