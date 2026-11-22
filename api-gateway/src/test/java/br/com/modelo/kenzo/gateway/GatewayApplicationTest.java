package br.com.modelo.kenzo.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GatewayApplicationTest {

    @Test
    void contextoSobe() {
        // se o contexto do Spring subir (com a rota do log-service) sem erro, o teste passa
    }
}
