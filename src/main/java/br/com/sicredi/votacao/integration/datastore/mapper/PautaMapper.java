package br.com.sicredi.votacao.integration.datastore.mapper;

import br.com.sicredi.votacao.core.domain.PautaDomain;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;

public class PautaMapper {

    public static PautaEntity paraEntity(PautaDomain pautaDomain){
        return PautaEntity.builder()
                .nome(pautaDomain.getNome())
                .descricao(pautaDomain.getDescricao())
                .votada(false)
                .build();
    }
}
