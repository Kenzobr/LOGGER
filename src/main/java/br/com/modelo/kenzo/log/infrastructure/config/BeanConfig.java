package br.com.modelo.kenzo.log.infrastructure.config;

import br.com.modelo.kenzo.log.core.usecase.BuscaAutomacaoPorIdUseCase;
import br.com.modelo.kenzo.log.core.usecase.CriaAutomacaoUseCase;
import br.com.modelo.kenzo.log.core.usecase.ListaAutomacoesUseCase;
import br.com.modelo.kenzo.log.core.usecase.ListaLogsUseCase;
import br.com.modelo.kenzo.log.core.usecase.RegistraLogUseCase;
import br.com.modelo.kenzo.log.dataprovider.repository.AutomacaoH2RepositoryAdapter;
import br.com.modelo.kenzo.log.dataprovider.repository.AutomacaoJpaRepository;
import br.com.modelo.kenzo.log.dataprovider.repository.LogAutomacaoJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {

    @Bean
    public AutomacaoH2RepositoryAdapter automacaoRepositoryAdapter(AutomacaoJpaRepository automacaoJpaRepository,
                                                                   LogAutomacaoJpaRepository logAutomacaoJpaRepository) {
        return new AutomacaoH2RepositoryAdapter(automacaoJpaRepository, logAutomacaoJpaRepository);
    }

    @Bean
    public CriaAutomacaoUseCase criaAutomacaoUseCase(AutomacaoH2RepositoryAdapter adapter) {
        return new CriaAutomacaoUseCase(adapter, adapter);
    }

    @Bean
    public RegistraLogUseCase registraLogUseCase(AutomacaoH2RepositoryAdapter adapter) {
        return new RegistraLogUseCase(adapter, adapter, adapter);
    }

    @Bean
    public ListaLogsUseCase listaLogsUseCase(AutomacaoH2RepositoryAdapter adapter) {
        return new ListaLogsUseCase(adapter, adapter);
    }

    @Bean
    public BuscaAutomacaoPorIdUseCase buscaAutomacaoPorIdUseCase(AutomacaoH2RepositoryAdapter adapter) {
        return new BuscaAutomacaoPorIdUseCase(adapter);
    }

    @Bean
    public ListaAutomacoesUseCase listaAutomacoesUseCase(AutomacaoH2RepositoryAdapter adapter) {
        return new ListaAutomacoesUseCase(adapter);
    }
}
