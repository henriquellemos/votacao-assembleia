package br.com.sicredi.votacao.core.mapper;

import br.com.sicredi.votacao.application.dto.request.SessaoRequest;
import br.com.sicredi.votacao.application.dto.response.SessaoResponse;
import br.com.sicredi.votacao.core.domain.PautaDomain;
import br.com.sicredi.votacao.core.domain.SessaoDomain;
import br.com.sicredi.votacao.integration.datastore.entity.SessaoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.SessaoStatus;

public class SessaoMapper {

    public static SessaoDomain paraIntegration(SessaoRequest sessao, PautaDomain pautaDomain){
        return SessaoDomain.builder()
                .pauta(pautaDomain)
                .sessaoStatus(SessaoStatus.ABERTA)
                .tempoSessao(sessao.getTempoSessao())
                .build();
    }

    public static SessaoResponse paraResponse(SessaoEntity entity){
        return SessaoResponse.builder()
                .id(entity.getId())
                .idPauta(entity.getPauta().getId())
                .sessaoStatus(entity.getSessaoStatus().name())
                .dataExpiracao(entity.getDataExpiracao())
                .build();
    }
}
