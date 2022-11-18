package br.com.sicredi.votacao.core.usecase;

import br.com.sicredi.votacao.application.dto.request.PautaRequest;
import br.com.sicredi.votacao.application.dto.response.PautaResponse;
import br.com.sicredi.votacao.core.port.PautaPort;
import org.springframework.stereotype.Service;

@Service
public class PautaUseCase implements PautaPort {
    @Override
    public PautaResponse cadastrar(PautaRequest request) {
        return PautaResponse.builder().build();
    }
}
