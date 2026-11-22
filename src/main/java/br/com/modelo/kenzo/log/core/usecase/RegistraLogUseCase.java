package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.Automacao;
import br.com.modelo.kenzo.log.core.domain.AutomacaoNaoEncontradaException;
import br.com.modelo.kenzo.log.core.domain.LogAutomacao;
import br.com.modelo.kenzo.log.core.domain.StatusAutomacao;


public class RegistraLogUseCase {

    private final BuscaAutomacaoPorIdRepository buscaAutomacaoPorIdRepository;
    private final SalvaAutomacaoRepository salvaAutomacaoRepository;
    private final SalvaLogRepository salvaLogRepository;

    public RegistraLogUseCase(BuscaAutomacaoPorIdRepository buscaAutomacaoPorIdRepository,
                              SalvaAutomacaoRepository salvaAutomacaoRepository,
                              SalvaLogRepository salvaLogRepository) {
        this.buscaAutomacaoPorIdRepository = buscaAutomacaoPorIdRepository;
        this.salvaAutomacaoRepository = salvaAutomacaoRepository;
        this.salvaLogRepository = salvaLogRepository;
    }

    public LogAutomacao executar(String automacaoId, StatusAutomacao status, String mensagem) {
        Automacao automacao = buscaAutomacaoPorIdRepository.buscarPorId(automacaoId)
                .orElseThrow(() -> new AutomacaoNaoEncontradaException(automacaoId));
        LogAutomacao log = automacao.atualizarStatus(status, mensagem);
        salvaAutomacaoRepository.salvar(automacao);
        return salvaLogRepository.salvarLog(log);
    }
}
