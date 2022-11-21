package br.com.sicredi.votacao.core.port.voto;

import br.com.sicredi.votacao.core.domain.PautaDomain;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import br.com.sicredi.votacao.integration.datastore.entity.VotoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.OpcaoVoto;

public interface VotoOutbound {

    VotoEntity salvar(OpcaoVoto voto, PautaEntity pauta);
    Long contabilizar(PautaDomain domain, OpcaoVoto opcao);
}
