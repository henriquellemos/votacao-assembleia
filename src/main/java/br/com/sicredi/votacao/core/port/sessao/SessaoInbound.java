package br.com.sicredi.votacao.core.port.sessao;

import br.com.sicredi.votacao.application.dto.request.SessaoRequest;
import br.com.sicredi.votacao.application.dto.response.SessaoResponse;

import java.util.UUID;

public interface SessaoInbound {

    SessaoResponse abrir(SessaoRequest request, UUID idPauta);
}
