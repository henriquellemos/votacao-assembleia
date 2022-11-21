package br.com.sicredi.votacao.integration.datastore.repository;

import br.com.sicredi.votacao.integration.datastore.entity.SessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessaoRepository extends JpaRepository<SessaoEntity, UUID> {
}
