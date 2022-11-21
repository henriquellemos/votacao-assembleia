package br.com.sicredi.votacao.core.mapper;

import br.com.sicredi.votacao.integration.datastore.entity.AssociadoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import br.com.sicredi.votacao.integration.datastore.entity.RegistroVotoEntity;

import java.time.LocalDateTime;

public class RegistroVotoMapper {

    public static RegistroVotoEntity paraEntity(PautaEntity pauta, AssociadoEntity associado) {
        return RegistroVotoEntity.builder()
                .associado(associado)
                .pautaVotada(pauta)
                .dataVoto(LocalDateTime.now())
                .build();
    }
}
