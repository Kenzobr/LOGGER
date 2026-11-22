package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.Automacao;
import br.com.modelo.kenzo.log.core.domain.LogAutomacao;
import br.com.modelo.kenzo.log.core.domain.Pagina;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/** Fake das portas do core para testar os use cases sem banco. */
public class RepositorioEmMemoria implements SalvaAutomacaoRepository, BuscaAutomacaoPorIdRepository,
        ListaAutomacoesRepository, SalvaLogRepository, ListaLogsRepository {

    private final Map<String, Automacao> automacoes = new LinkedHashMap<>();
    private final List<LogAutomacao> logs = new ArrayList<>();
    private final AtomicLong sequencia = new AtomicLong();

    @Override
    public Automacao salvar(Automacao automacao) {
        automacoes.put(automacao.getId(), automacao);
        return automacao;
    }

    @Override
    public Optional<Automacao> buscarPorId(String id) {
        return Optional.ofNullable(automacoes.get(id));
    }

    @Override
    public Pagina<Automacao> listar(int pagina, int tamanho) {
        List<Automacao> todas = new ArrayList<>(automacoes.values());
        int inicio = Math.min(pagina * tamanho, todas.size());
        int fim = Math.min(inicio + tamanho, todas.size());
        int totalPaginas = (int) Math.ceil((double) todas.size() / tamanho);
        return new Pagina<>(todas.subList(inicio, fim), pagina, tamanho, todas.size(), totalPaginas);
    }

    @Override
    public LogAutomacao salvarLog(LogAutomacao log) {
        LogAutomacao salvo = new LogAutomacao(sequencia.incrementAndGet(), log.automacaoId(), log.status(),
                log.mensagem(), log.timestamp());
        logs.add(salvo);
        return salvo;
    }

    @Override
    public List<LogAutomacao> listarLogs(String automacaoId) {
        return logs.stream().filter(l -> l.automacaoId().equals(automacaoId)).toList();
    }
}
