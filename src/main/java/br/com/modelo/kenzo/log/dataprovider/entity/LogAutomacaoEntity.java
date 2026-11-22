package br.com.modelo.kenzo.log.dataprovider.entity;

import br.com.modelo.kenzo.log.core.domain.StatusAutomacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_log_automacao", indexes = @Index(name = "idx_log_automacao_id", columnList = "automacao_id"))
public class LogAutomacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "automacao_id", nullable = false)
    private String automacaoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAutomacao status;

    @Column(length = 4000)
    private String mensagem;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    protected LogAutomacaoEntity() {
    }

    public LogAutomacaoEntity(Long id, String automacaoId, StatusAutomacao status, String mensagem, LocalDateTime timestamp) {
        this.id = id;
        this.automacaoId = automacaoId;
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getAutomacaoId() {
        return automacaoId;
    }

    public StatusAutomacao getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
