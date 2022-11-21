package br.com.sicredi.votacao.integration.datastore.mapper;

import br.com.sicredi.votacao.core.domain.SessaoDomain;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import br.com.sicredi.votacao.integration.datastore.entity.SessaoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.SessaoStatus;

import java.time.LocalDateTime;

public class SessaoMapper {

    public static SessaoEntity paraEntity(SessaoDomain domain, PautaEntity pautaEntity){
        return SessaoEntity.builder()
                .pauta(pautaEntity)
                .dataExpiracao(LocalDateTime.now().plusMinutes(domain.getTempoSessao()))
                .sessaoStatus(SessaoStatus.ABERTA)
                .build();
    }
}
