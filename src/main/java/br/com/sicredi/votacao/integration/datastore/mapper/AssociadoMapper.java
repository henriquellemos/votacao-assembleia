package br.com.sicredi.votacao.integration.datastore.mapper;

import br.com.sicredi.votacao.core.domain.AssociadoDomain;
import br.com.sicredi.votacao.integration.datastore.entity.AssociadoEntity;

public class AssociadoMapper {

    public static AssociadoEntity paraEntity(AssociadoDomain domain){
        return AssociadoEntity.builder()
                .cpf(domain.getCpf())
                .nome(domain.getNome())
                .status(domain.getStatus())
                .build();
    }
}
