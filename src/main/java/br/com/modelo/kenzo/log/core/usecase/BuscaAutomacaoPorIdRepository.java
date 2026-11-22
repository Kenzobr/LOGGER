package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.Automacao;

import java.util.Optional;

public interface BuscaAutomacaoPorIdRepository {

    Optional<Automacao> buscarPorId(String id);
}
