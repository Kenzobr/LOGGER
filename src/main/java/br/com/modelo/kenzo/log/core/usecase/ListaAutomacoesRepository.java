package br.com.modelo.kenzo.log.core.usecase;

import br.com.modelo.kenzo.log.core.domain.Automacao;
import br.com.modelo.kenzo.log.core.domain.Pagina;

public interface ListaAutomacoesRepository {

    Pagina<Automacao> listar(int pagina, int tamanho);
}
