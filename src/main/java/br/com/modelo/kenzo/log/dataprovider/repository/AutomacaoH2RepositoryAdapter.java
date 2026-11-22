package br.com.modelo.kenzo.log.dataprovider.repository;

import br.com.modelo.kenzo.log.core.domain.Automacao;
import br.com.modelo.kenzo.log.core.domain.LogAutomacao;
import br.com.modelo.kenzo.log.core.domain.Pagina;
import br.com.modelo.kenzo.log.core.usecase.BuscaAutomacaoPorIdRepository;
import br.com.modelo.kenzo.log.core.usecase.ListaAutomacoesRepository;
import br.com.modelo.kenzo.log.core.usecase.ListaLogsRepository;
import br.com.modelo.kenzo.log.core.usecase.SalvaAutomacaoRepository;
import br.com.modelo.kenzo.log.core.usecase.SalvaLogRepository;
import br.com.modelo.kenzo.log.dataprovider.entity.AutomacaoEntity;
import br.com.modelo.kenzo.log.dataprovider.entity.LogAutomacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class AutomacaoH2RepositoryAdapter implements SalvaAutomacaoRepository, BuscaAutomacaoPorIdRepository,
        ListaAutomacoesRepository, SalvaLogRepository, ListaLogsRepository {

    private final AutomacaoJpaRepository automacaoJpaRepository;
    private final LogAutomacaoJpaRepository logAutomacaoJpaRepository;

    public AutomacaoH2RepositoryAdapter(AutomacaoJpaRepository automacaoJpaRepository,
                                        LogAutomacaoJpaRepository logAutomacaoJpaRepository) {
        this.automacaoJpaRepository = automacaoJpaRepository;
        this.logAutomacaoJpaRepository = logAutomacaoJpaRepository;
    }

    @Override
    public Automacao salvar(Automacao automacao) {
        return paraDominio(automacaoJpaRepository.save(paraEntity(automacao)));
    }

    @Override
    public Optional<Automacao> buscarPorId(String id) {
        return automacaoJpaRepository.findById(id).map(this::paraDominio);
    }

    @Override
    public Pagina<Automacao> listar(int pagina, int tamanho) {
        Page<AutomacaoEntity> page = automacaoJpaRepository.findAll(
                PageRequest.of(pagina, tamanho, Sort.by(Sort.Direction.DESC, "criadoEm")));
        List<Automacao> conteudo = page.getContent().stream().map(this::paraDominio).toList();
        return new Pagina<>(conteudo, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public LogAutomacao salvarLog(LogAutomacao log) {
        return paraDominio(logAutomacaoJpaRepository.save(paraEntity(log)));
    }

    @Override
    public List<LogAutomacao> listarLogs(String automacaoId) {
        return logAutomacaoJpaRepository.findByAutomacaoIdOrderByIdAsc(automacaoId)
                .stream().map(this::paraDominio).toList();
    }

    private AutomacaoEntity paraEntity(Automacao a) {
        return new AutomacaoEntity(a.getId(), a.getNome(), a.getStatus(), a.getCriadoEm());
    }

    private Automacao paraDominio(AutomacaoEntity e) {
        return new Automacao(e.getId(), e.getNome(), e.getStatus(), e.getCriadoEm());
    }

    private LogAutomacaoEntity paraEntity(LogAutomacao l) {
        return new LogAutomacaoEntity(l.id(), l.automacaoId(), l.status(), l.mensagem(), l.timestamp());
    }

    private LogAutomacao paraDominio(LogAutomacaoEntity e) {
        return new LogAutomacao(e.getId(), e.getAutomacaoId(), e.getStatus(), e.getMensagem(), e.getTimestamp());
    }
}
