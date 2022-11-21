package br.com.sicredi.votacao.integration.datastore.repository;

import br.com.sicredi.votacao.integration.datastore.entity.AssociadoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import br.com.sicredi.votacao.integration.datastore.entity.RegistroVotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistroVotoRepository extends JpaRepository<RegistroVotoEntity, UUID> {
    boolean existsByAssociadoAndPautaVotada(AssociadoEntity associado, PautaEntity pautaVotada);
}
