package br.com.sicredi.votacao.integration.datastore.mapper;

import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import br.com.sicredi.votacao.integration.datastore.entity.VotoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.OpcaoVoto;

public class VotoMapper {

    public static VotoEntity paraEntity(OpcaoVoto voto, PautaEntity pautaEntity){
        return VotoEntity.builder()
                .voto(voto)
                .pauta(pautaEntity)
                .build();
    }
}
