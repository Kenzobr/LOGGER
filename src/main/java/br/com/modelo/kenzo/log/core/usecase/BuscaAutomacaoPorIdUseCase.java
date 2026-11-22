package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.Automacao;
import br.com.modelo.kenzo.log.core.domain.AutomacaoNaoEncontradaException;

public class BuscaAutomacaoPorIdUseCase {

    private final BuscaAutomacaoPorIdRepository buscaAutomacaoPorIdRepository;

    public BuscaAutomacaoPorIdUseCase(BuscaAutomacaoPorIdRepository buscaAutomacaoPorIdRepository) {
        this.buscaAutomacaoPorIdRepository = buscaAutomacaoPorIdRepository;
    }

    public Automacao executar(String id) {
        return buscaAutomacaoPorIdRepository.buscarPorId(id)
                .orElseThrow(() -> new AutomacaoNaoEncontradaException(id));
    }
}
