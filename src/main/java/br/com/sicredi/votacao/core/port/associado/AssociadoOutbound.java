package br.com.sicredi.votacao.core.port.associado;

import br.com.sicredi.votacao.core.domain.AssociadoDomain;
import br.com.sicredi.votacao.integration.datastore.entity.AssociadoEntity;

public interface AssociadoOutbound {

    AssociadoEntity cadastrar(AssociadoDomain domain);

    AssociadoEntity buscarPorId(Long cpfAssociado);
}
