package br.com.sicredi.votacao.integration.datastore.repository;

import br.com.sicredi.votacao.integration.datastore.entity.AssociadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AssociadoRepository extends JpaRepository<AssociadoEntity, Long> {
}
