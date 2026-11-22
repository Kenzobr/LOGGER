package br.com.modelo.kenzo.log.entrypoint.controller;

import br.com.modelo.kenzo.log.core.domain.Automacao;
import br.com.modelo.kenzo.log.core.usecase.BuscaAutomacaoPorIdUseCase;
import br.com.modelo.kenzo.log.core.usecase.CriaAutomacaoUseCase;
import br.com.modelo.kenzo.log.core.usecase.ListaAutomacoesUseCase;
import br.com.modelo.kenzo.log.core.usecase.ListaLogsUseCase;
import br.com.modelo.kenzo.log.core.usecase.RegistraLogUseCase;
import br.com.modelo.kenzo.log.core.usecase.RepositorioEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Testa o controller sem subir o Spring (MockMvc standalone + repositorio em memoria). */
class AutomacaoControllerTest {

    private MockMvc mockMvc;
    private CriaAutomacaoUseCase cria;

    @BeforeEach
    void setUp() {
        RepositorioEmMemoria repo = new RepositorioEmMemoria();
        cria = new CriaAutomacaoUseCase(repo, repo);
        AutomacaoController controller = new AutomacaoController(
                cria,
                new RegistraLogUseCase(repo, repo, repo),
                new ListaLogsUseCase(repo, repo),
                new BuscaAutomacaoPorIdUseCase(repo),
                new ListaAutomacoesUseCase(repo));
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new AutomacaoExceptionHandler())
                .build();
    }

    @Test
    void postCriaAutomacao() throws Exception {
        mockMvc.perform(post("/automacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"ADM COLOCOU ARQUIVO NA PASTA\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.nome").value("ADM COLOCOU ARQUIVO NA PASTA"));
    }

    @Test
    void postLogEGetListaLogs() throws Exception {
        Automacao automacao = cria.executar("robo");

        mockMvc.perform(post("/automacoes/{id}/logs", automacao.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"STARTED\",\"mensagem\":\"ASSINOU E FOI PARA PASTA\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("STARTED"));

        mockMvc.perform(get("/automacoes/{id}/logs", automacao.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].mensagem").value("ASSINOU E FOI PARA PASTA"));
    }

    @Test
    void getAutomacaoPorIdEListagem() throws Exception {
        Automacao automacao = cria.executar("robo");

        mockMvc.perform(get("/automacoes/{id}", automacao.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(automacao.getId()));

        mockMvc.perform(get("/automacoes").param("pagina", "0").param("tamanho", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElementos").value(1));
    }

    @Test
    void automacaoInexistenteRetorna404() throws Exception {
        mockMvc.perform(get("/automacoes/id-inexistente/logs"))
                .andExpect(status().isNotFound());
    }

    @Test
    void statusInvalidoRetorna400() throws Exception {
        Automacao automacao = cria.executar("robo");

        mockMvc.perform(post("/automacoes/{id}/logs", automacao.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"QUALQUER\",\"mensagem\":\"x\"}"))
                .andExpect(status().isBadRequest());
    }
}
