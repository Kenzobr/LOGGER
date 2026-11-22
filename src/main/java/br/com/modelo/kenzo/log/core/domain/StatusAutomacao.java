package br.com.modelo.kenzo.log.core.domain;

public enum StatusAutomacao {
    CREATED,
    STARTED,
    PROCESSING,
    COMPLETED,
    FAILED;

    public static StatusAutomacao de(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("status e obrigatorio");
        }
        try {
            return StatusAutomacao.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("status invalido: " + valor);
        }
    }
}
