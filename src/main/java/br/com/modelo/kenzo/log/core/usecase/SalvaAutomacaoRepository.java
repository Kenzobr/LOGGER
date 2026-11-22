package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.Automacao;

public interface SalvaAutomacaoRepository {

    Automacao salvar(Automacao automacao);
}
