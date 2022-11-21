package br.com.sicredi.votacao.core.mapper;

import br.com.sicredi.votacao.application.dto.request.AssociadoRequest;
import br.com.sicredi.votacao.application.dto.response.AssociadoResponse;
import br.com.sicredi.votacao.core.domain.AssociadoDomain;
import br.com.sicredi.votacao.integration.datastore.entity.AssociadoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.StatusVoto;

public class AssociadoMapper {

    public static AssociadoDomain paraIntegration(AssociadoRequest request, StatusVoto statusVoto){
        return AssociadoDomain.builder()
                .cpf(request.getCpf())
                .nome(request.getNome())
                .status(statusVoto)
                .build();
    }

    public static AssociadoResponse paraResponse(AssociadoEntity entity) {
        return AssociadoResponse.builder()
                .cpf(entity.getCpf())
                .nome(entity.getNome())
                .status(entity.getStatus())
                .build();
    }
}
