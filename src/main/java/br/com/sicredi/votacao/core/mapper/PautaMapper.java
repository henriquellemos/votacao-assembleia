package br.com.sicredi.votacao.core.mapper;

import br.com.sicredi.votacao.application.dto.request.PautaRequest;
import br.com.sicredi.votacao.core.domain.PautaDomain;

public class PautaMapper {

    public static PautaDomain paraIntegration(PautaRequest pauta){
        return PautaDomain.builder()
                .nome(pauta.getNome())
                .descricao(pauta.getDescricao())
                .build();
    }
}
