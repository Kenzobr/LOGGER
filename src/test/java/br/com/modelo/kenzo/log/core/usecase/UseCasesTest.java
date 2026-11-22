package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.Automacao;
import br.com.modelo.kenzo.log.core.domain.AutomacaoNaoEncontradaException;
import br.com.modelo.kenzo.log.core.domain.LogAutomacao;
import br.com.modelo.kenzo.log.core.domain.Pagina;
import br.com.modelo.kenzo.log.core.domain.StatusAutomacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UseCasesTest {

    private RepositorioEmMemoria repo;
    private CriaAutomacaoUseCase cria;
    private RegistraLogUseCase registra;
    private ListaLogsUseCase listaLogs;
    private BuscaAutomacaoPorIdUseCase busca;
    private ListaAutomacoesUseCase lista;

    @BeforeEach
    void setUp() {
        repo = new RepositorioEmMemoria();
        cria = new CriaAutomacaoUseCase(repo, repo);
        registra = new RegistraLogUseCase(repo, repo, repo);
        listaLogs = new ListaLogsUseCase(repo, repo);
        busca = new BuscaAutomacaoPorIdUseCase(repo);
        lista = new ListaAutomacoesUseCase(repo);
    }

    @Test
    void criaAutomacaoComLogInicialCreated() {
        Automacao automacao = cria.executar("ADM COLOCOU ARQUIVO NA PASTA");

        assertThat(automacao.getId()).isNotBlank();
        assertThat(automacao.getStatus()).isEqualTo(StatusAutomacao.CREATED);

        List<LogAutomacao> logs = listaLogs.executar(automacao.getId());
        assertThat(logs).hasSize(1);
        assertThat(logs.getFirst().mensagem()).isEqualTo("ADM COLOCOU ARQUIVO NA PASTA");
    }

    @Test
    void registraLogsNaOrdemEAtualizaStatus() {
        Automacao automacao = cria.executar("robo");

        registra.executar(automacao.getId(), StatusAutomacao.STARTED, "ASSINOU E FOI PARA PASTA");
        registra.executar(automacao.getId(), StatusAutomacao.PROCESSING, "Fechando Automacao");
        LogAutomacao ultimo = registra.executar(automacao.getId(), StatusAutomacao.COMPLETED, "Processamento concluido");

        assertThat(ultimo.id()).isNotNull();
        assertThat(busca.executar(automacao.getId()).getStatus()).isEqualTo(StatusAutomacao.COMPLETED);
        assertThat(listaLogs.executar(automacao.getId()))
                .extracting(LogAutomacao::status)
                .containsExactly(StatusAutomacao.CREATED, StatusAutomacao.STARTED,
                        StatusAutomacao.PROCESSING, StatusAutomacao.COMPLETED);
    }

    @Test
    void registrarLogEmAutomacaoInexistenteLancaExcecao() {
        assertThatThrownBy(() -> registra.executar("nao-existe", StatusAutomacao.STARTED, "x"))
                .isInstanceOf(AutomacaoNaoEncontradaException.class);
    }

    @Test
    void listarLogsDeAutomacaoInexistenteLancaExcecao() {
        assertThatThrownBy(() -> listaLogs.executar("nao-existe"))
                .isInstanceOf(AutomacaoNaoEncontradaException.class);
    }

    @Test
    void buscarInexistenteLancaExcecao() {
        assertThatThrownBy(() -> busca.executar("nao-existe"))
                .isInstanceOf(AutomacaoNaoEncontradaException.class)
                .hasMessageContaining("nao-existe");
    }

    @Test
    void listaPaginada() {
        cria.executar("a");
        cria.executar("b");
        cria.executar("c");

        Pagina<Automacao> pagina = lista.executar(0, 2);

        assertThat(pagina.conteudo()).hasSize(2);
        assertThat(pagina.totalElementos()).isEqualTo(3);
        assertThat(pagina.totalPaginas()).isEqualTo(2);
    }

    @Test
    void listaComParametrosInvalidos() {
        assertThatThrownBy(() -> lista.executar(-1, 10)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> lista.executar(0, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> lista.executar(0, 101)).isInstanceOf(IllegalArgumentException.class);
    }
}
