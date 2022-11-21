package br.com.sicredi.votacao.core.mapper;

import br.com.sicredi.votacao.application.dto.request.PautaRequest;
import br.com.sicredi.votacao.core.domain.PautaDomain;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;

public class PautaMapper {

    public static PautaDomain paraIntegration(PautaRequest pautaRequest){
        return PautaDomain.builder()
                .nome(pautaRequest.getNome())
                .descricao(pautaRequest.getDescricao())
                .build();
    }

    public static PautaDomain paraDomain(PautaEntity pautaEntity){
        return PautaDomain.builder()
                .id(pautaEntity.getId())
                .nome(pautaEntity.getNome())
                .descricao(pautaEntity.getDescricao())
                .votada(pautaEntity.getVotada())
                .totalVotos(pautaEntity.getTotalVotos())
                .build();
    }
}
