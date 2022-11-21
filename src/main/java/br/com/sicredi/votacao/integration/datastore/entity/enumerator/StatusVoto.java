package br.com.sicredi.votacao.integration.datastore.entity.enumerator;

import br.com.sicredi.votacao.core.exception.ServicoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum StatusVoto {
    HABILITADO_PARA_VOTO, DESABILITADO_PARA_VOTO;

    public static StatusVoto transformar(String status){
        if ("ABLE_TO_VOTE".equalsIgnoreCase(status)) {
            return StatusVoto.HABILITADO_PARA_VOTO;
        } else if ("UNABLE_TO_VOTE".equalsIgnoreCase(status)) {
            return StatusVoto.DESABILITADO_PARA_VOTO;
        } else {
            log.error("Status '{}' não encontrado", status);
            throw new ServicoException("Status de voto '" + status + "' não encontrado.");
        }
    }
}
