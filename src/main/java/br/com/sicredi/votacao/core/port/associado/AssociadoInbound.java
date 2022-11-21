package br.com.sicredi.votacao.core.port.associado;

import br.com.sicredi.votacao.application.dto.request.AssociadoRequest;
import br.com.sicredi.votacao.application.dto.response.AssociadoResponse;

public interface AssociadoInbound {

    AssociadoResponse cadastrar(AssociadoRequest request);
}
