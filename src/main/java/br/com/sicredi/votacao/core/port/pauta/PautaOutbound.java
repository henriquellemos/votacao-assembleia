package br.com.sicredi.votacao.core.port.pauta;

import br.com.sicredi.votacao.core.domain.PautaDomain;

import java.util.Optional;
import java.util.UUID;

public interface PautaOutbound {

    Optional<UUID> cadastrar(PautaDomain domain);
}
