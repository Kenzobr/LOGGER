package br.com.modelo.kenzo.log;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Sobe o contexto completo (BeanConfig + JPA + H2 em memoria), com Eureka desligado. */
@SpringBootTest
class LogApplicationTest {

    @Autowired
    private WebApplicationContext context;

    @Test
    void fluxoCompletoNoBanco() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        MvcResult criado = mockMvc.perform(post("/automacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"robo integracao\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        String body = criado.getResponse().getContentAsString();
        String id = body.replaceAll(".*\"id\"\\s*:\\s*\"([^\"]+)\".*", "$1");

        mockMvc.perform(post("/automacoes/{id}/logs", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"COMPLETED\",\"mensagem\":\"Processamento concluido\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/automacoes/{id}/logs", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/automacoes/{id}", id))
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        mockMvc.perform(get("/automacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElementos").value(1));
    }
}
