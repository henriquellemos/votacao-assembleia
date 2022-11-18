package br.com.sicredi.votacao.integration.datastore.repository;

import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PautaRepository extends JpaRepository<PautaEntity, UUID> {
}
