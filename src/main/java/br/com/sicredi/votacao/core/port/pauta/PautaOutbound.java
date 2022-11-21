package br.com.sicredi.votacao.core.port.pauta;

import br.com.sicredi.votacao.core.domain.PautaDomain;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;

import java.util.Optional;
import java.util.UUID;

public interface PautaOutbound {

    Optional<UUID> cadastrar(PautaDomain domain);
    PautaEntity buscarPorId(UUID id);

    PautaEntity atualizar(PautaEntity pauta);
}
