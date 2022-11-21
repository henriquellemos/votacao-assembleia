package br.com.sicredi.votacao.core.port.voto;

import br.com.sicredi.votacao.integration.datastore.entity.AssociadoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import br.com.sicredi.votacao.integration.datastore.entity.SessaoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.OpcaoVoto;

public interface RegistroVotoOutbound {

    Boolean efetuarVoto(PautaEntity pauta, SessaoEntity sessao, AssociadoEntity associado, OpcaoVoto voto);
}
