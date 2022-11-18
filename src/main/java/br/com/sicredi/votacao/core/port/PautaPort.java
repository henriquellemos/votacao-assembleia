package br.com.sicredi.votacao.core.port;

import br.com.sicredi.votacao.application.dto.request.PautaRequest;
import br.com.sicredi.votacao.application.dto.response.PautaResponse;

public interface PautaPort {

    PautaResponse cadastrar(PautaRequest request);
}
