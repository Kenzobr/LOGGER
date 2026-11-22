package br.com.modelo.kenzo.log.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Automacao {

    private final String id;
    private final String nome;
    private StatusAutomacao status;
    private final LocalDateTime criadoEm;

    public Automacao(String id, String nome, StatusAutomacao status, LocalDateTime criadoEm) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("nome da automacao e obrigatorio");
        }
        this.id = id;
        this.nome = nome;
        this.status = status == null ? StatusAutomacao.CREATED : status;
        this.criadoEm = criadoEm == null ? LocalDateTime.now() : criadoEm;
    }

    public static Automacao nova(String nome) {
        return new Automacao(UUID.randomUUID().toString(), nome, StatusAutomacao.CREATED, LocalDateTime.now());
    }


    public LogAutomacao atualizarStatus(StatusAutomacao novoStatus, String mensagem) {
        if (novoStatus == null) {
            throw new IllegalArgumentException("status e obrigatorio");
        }
        this.status = novoStatus;
        return new LogAutomacao(null, id, novoStatus, mensagem, LocalDateTime.now());
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public StatusAutomacao getStatus() {
        return status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
