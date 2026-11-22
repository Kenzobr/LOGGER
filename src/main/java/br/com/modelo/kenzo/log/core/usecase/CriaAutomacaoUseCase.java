package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.Automacao;
import br.com.modelo.kenzo.log.core.domain.StatusAutomacao;


public class CriaAutomacaoUseCase {

    private final SalvaAutomacaoRepository salvaAutomacaoRepository;
    private final SalvaLogRepository salvaLogRepository;

    public CriaAutomacaoUseCase(SalvaAutomacaoRepository salvaAutomacaoRepository, SalvaLogRepository salvaLogRepository) {
        this.salvaAutomacaoRepository = salvaAutomacaoRepository;
        this.salvaLogRepository = salvaLogRepository;
    }

    public Automacao executar(String nome) {
        Automacao automacao = Automacao.nova(nome);
        var logInicial = automacao.atualizarStatus(StatusAutomacao.CREATED, nome);
        Automacao salva = salvaAutomacaoRepository.salvar(automacao);
        salvaLogRepository.salvarLog(logInicial);
        return salva;
    }
}
