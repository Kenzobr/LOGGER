package br.com.modelo.kenzo.log.dataprovider.entity;

import br.com.modelo.kenzo.log.core.domain.StatusAutomacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_automacao")
public class AutomacaoEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAutomacao status;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    protected AutomacaoEntity() {
    }

    public AutomacaoEntity(String id, String nome, StatusAutomacao status, LocalDateTime criadoEm) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.criadoEm = criadoEm;
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
