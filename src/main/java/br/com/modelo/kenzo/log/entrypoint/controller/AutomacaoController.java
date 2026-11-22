package br.com.modelo.kenzo.log.entrypoint.controller;

import br.com.modelo.kenzo.log.core.domain.Automacao;
import br.com.modelo.kenzo.log.core.domain.LogAutomacao;
import br.com.modelo.kenzo.log.core.domain.Pagina;
import br.com.modelo.kenzo.log.core.domain.StatusAutomacao;
import br.com.modelo.kenzo.log.core.usecase.BuscaAutomacaoPorIdUseCase;
import br.com.modelo.kenzo.log.core.usecase.CriaAutomacaoUseCase;
import br.com.modelo.kenzo.log.core.usecase.ListaAutomacoesUseCase;
import br.com.modelo.kenzo.log.core.usecase.ListaLogsUseCase;
import br.com.modelo.kenzo.log.core.usecase.RegistraLogUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/automacoes")
public class AutomacaoController {

    private final CriaAutomacaoUseCase criaAutomacaoUseCase;
    private final RegistraLogUseCase registraLogUseCase;
    private final ListaLogsUseCase listaLogsUseCase;
    private final BuscaAutomacaoPorIdUseCase buscaAutomacaoPorIdUseCase;
    private final ListaAutomacoesUseCase listaAutomacoesUseCase;

    public AutomacaoController(CriaAutomacaoUseCase criaAutomacaoUseCase,
                               RegistraLogUseCase registraLogUseCase,
                               ListaLogsUseCase listaLogsUseCase,
                               BuscaAutomacaoPorIdUseCase buscaAutomacaoPorIdUseCase,
                               ListaAutomacoesUseCase listaAutomacoesUseCase) {
        this.criaAutomacaoUseCase = criaAutomacaoUseCase;
        this.registraLogUseCase = registraLogUseCase;
        this.listaLogsUseCase = listaLogsUseCase;
        this.buscaAutomacaoPorIdUseCase = buscaAutomacaoPorIdUseCase;
        this.listaAutomacoesUseCase = listaAutomacoesUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Automacao criar(@RequestBody CriaAutomacaoRequest request) {
        return criaAutomacaoUseCase.executar(request.nome());
    }

    @PostMapping("/{id}/logs")
    @ResponseStatus(HttpStatus.CREATED)
    public LogAutomacao registrarLog(@PathVariable String id, @RequestBody RegistraLogRequest request) {
        return registraLogUseCase.executar(id, StatusAutomacao.de(request.status()), request.mensagem());
    }

    @GetMapping("/{id}/logs")
    public List<LogAutomacao> listarLogs(@PathVariable String id) {
        return listaLogsUseCase.executar(id);
    }

    @GetMapping("/{id}")
    public Automacao buscarPorId(@PathVariable String id) {
        return buscaAutomacaoPorIdUseCase.executar(id);
    }

    @GetMapping
    public Pagina<Automacao> listar(@RequestParam(defaultValue = "0") int pagina,
                                    @RequestParam(defaultValue = "10") int tamanho) {
        return listaAutomacoesUseCase.executar(pagina, tamanho);
    }
}
