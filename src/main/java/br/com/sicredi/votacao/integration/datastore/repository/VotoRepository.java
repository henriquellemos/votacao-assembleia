package br.com.sicredi.votacao.integration.datastore.repository;

import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import br.com.sicredi.votacao.integration.datastore.entity.VotoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.OpcaoVoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VotoRepository extends JpaRepository<VotoEntity, UUID> {
    Long countByPautaAndVoto(PautaEntity pauta, OpcaoVoto voto);
}