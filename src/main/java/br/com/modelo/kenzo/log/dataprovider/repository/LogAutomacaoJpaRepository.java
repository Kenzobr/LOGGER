package br.com.modelo.kenzo.log.dataprovider.repository;

import br.com.modelo.kenzo.log.dataprovider.entity.LogAutomacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogAutomacaoJpaRepository extends JpaRepository<LogAutomacaoEntity, Long> {

    List<LogAutomacaoEntity> findByAutomacaoIdOrderByIdAsc(String automacaoId);
}
