package br.com.sicredi.votacao.core.port.pauta;

import br.com.sicredi.votacao.application.dto.request.PautaRequest;
import br.com.sicredi.votacao.application.dto.request.VotacaoRequest;
import br.com.sicredi.votacao.application.dto.response.VotacaoResponse;

import java.util.Optional;
import java.util.UUID;

public interface PautaInbound {

    Optional<UUID> cadastrar(PautaRequest request);

    VotacaoResponse votar(UUID idPauta, UUID idSessao, Long cpfAssociado, VotacaoRequest request);
}
