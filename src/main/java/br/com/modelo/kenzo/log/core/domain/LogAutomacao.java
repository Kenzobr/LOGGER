package br.com.modelo.kenzo.log.core.domain;

import java.time.LocalDateTime;


public record LogAutomacao(Long id, String automacaoId, StatusAutomacao status, String mensagem, LocalDateTime timestamp) {

    public LogAutomacao {
        if (automacaoId == null || automacaoId.isBlank()) {
            throw new IllegalArgumentException("automacaoId e obrigatorio");
        }
        if (status == null) {
            throw new IllegalArgumentException("status e obrigatorio");
        }
        mensagem = mensagem == null ? "" : mensagem;
        timestamp = timestamp == null ? LocalDateTime.now() : timestamp;
    }
}
