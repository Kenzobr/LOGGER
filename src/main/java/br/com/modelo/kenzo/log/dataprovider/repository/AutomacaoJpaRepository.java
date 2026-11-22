package br.com.modelo.kenzo.log.dataprovider.repository;

import br.com.modelo.kenzo.log.dataprovider.entity.AutomacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomacaoJpaRepository extends JpaRepository<AutomacaoEntity, String> {
}
