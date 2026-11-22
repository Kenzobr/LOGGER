package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.AutomacaoNaoEncontradaException;
import br.com.modelo.kenzo.log.core.domain.LogAutomacao;

import java.util.List;


public class ListaLogsUseCase {

    private final BuscaAutomacaoPorIdRepository buscaAutomacaoPorIdRepository;
    private final ListaLogsRepository listaLogsRepository;

    public ListaLogsUseCase(BuscaAutomacaoPorIdRepository buscaAutomacaoPorIdRepository, ListaLogsRepository listaLogsRepository) {
        this.buscaAutomacaoPorIdRepository = buscaAutomacaoPorIdRepository;
        this.listaLogsRepository = listaLogsRepository;
    }

    public List<LogAutomacao> executar(String automacaoId) {
        if (buscaAutomacaoPorIdRepository.buscarPorId(automacaoId).isEmpty()) {
            throw new AutomacaoNaoEncontradaException(automacaoId);
        }
        return listaLogsRepository.listarLogs(automacaoId);
    }
}
