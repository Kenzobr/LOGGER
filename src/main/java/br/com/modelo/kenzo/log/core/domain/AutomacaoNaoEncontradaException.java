package br.com.modelo.kenzo.log.core.domain;

public class AutomacaoNaoEncontradaException extends RuntimeException {

    public AutomacaoNaoEncontradaException(String id) {
        super("Automacao nao encontrada: " + id);
    }
}
