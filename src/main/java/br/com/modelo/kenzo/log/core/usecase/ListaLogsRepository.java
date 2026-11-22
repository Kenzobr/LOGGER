package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.LogAutomacao;

import java.util.List;

public interface ListaLogsRepository {

    List<LogAutomacao> listarLogs(String automacaoId);
}
