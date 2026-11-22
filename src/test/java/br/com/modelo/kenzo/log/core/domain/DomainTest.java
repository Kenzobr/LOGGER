package br.com.modelo.kenzo.log.core.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DomainTest {

    @Test
    void novaAutomacaoComecaComoCreated() {
        Automacao automacao = Automacao.nova("robo");

        assertThat(automacao.getStatus()).isEqualTo(StatusAutomacao.CREATED);
        assertThat(automacao.getCriadoEm()).isNotNull();
        assertThat(automacao.getNome()).isEqualTo("robo");
    }

    @Test
    void automacaoSemNomeEhInvalida() {
        assertThatThrownBy(() -> Automacao.nova(" ")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Automacao.nova(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void atualizarStatusGeraLog() {
        Automacao automacao = Automacao.nova("robo");

        LogAutomacao log = automacao.atualizarStatus(StatusAutomacao.FAILED, "DEU ERRO");

        assertThat(automacao.getStatus()).isEqualTo(StatusAutomacao.FAILED);
        assertThat(log.automacaoId()).isEqualTo(automacao.getId());
        assertThat(log.mensagem()).isEqualTo("DEU ERRO");
        assertThat(log.timestamp()).isNotNull();
    }

    @Test
    void atualizarComStatusNuloEhInvalido() {
        Automacao automacao = Automacao.nova("robo");
        assertThatThrownBy(() -> automacao.atualizarStatus(null, "x")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void logComMensagemNulaViraVazia() {
        LogAutomacao log = new LogAutomacao(null, "id", StatusAutomacao.STARTED, null, null);
        assertThat(log.mensagem()).isEmpty();
        assertThat(log.timestamp()).isNotNull();
    }

    @Test
    void statusConverteTextoIgnorandoMaiusculas() {
        assertThat(StatusAutomacao.de("processing")).isEqualTo(StatusAutomacao.PROCESSING);
        assertThatThrownBy(() -> StatusAutomacao.de("XYZ")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> StatusAutomacao.de("")).isInstanceOf(IllegalArgumentException.class);
    }
}
