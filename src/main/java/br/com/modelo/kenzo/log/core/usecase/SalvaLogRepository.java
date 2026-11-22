package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.LogAutomacao;

public interface SalvaLogRepository {

    LogAutomacao salvarLog(LogAutomacao log);
}
