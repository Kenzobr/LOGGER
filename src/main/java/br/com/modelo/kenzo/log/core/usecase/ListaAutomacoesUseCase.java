package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.Automacao;
import br.com.modelo.kenzo.log.core.domain.Pagina;

public class ListaAutomacoesUseCase {

    private static final int TAMANHO_MAXIMO = 100;

    private final ListaAutomacoesRepository listaAutomacoesRepository;

    public ListaAutomacoesUseCase(ListaAutomacoesRepository listaAutomacoesRepository) {
        this.listaAutomacoesRepository = listaAutomacoesRepository;
    }

    public Pagina<Automacao> executar(int pagina, int tamanho) {
        if (pagina < 0) {
            throw new IllegalArgumentException("pagina nao pode ser negativa");
        }
        if (tamanho < 1 || tamanho > TAMANHO_MAXIMO) {
            throw new IllegalArgumentException("tamanho deve estar entre 1 e " + TAMANHO_MAXIMO);
        }
        return listaAutomacoesRepository.listar(pagina, tamanho);
    }
}
