package br.com.sicredi.votacao.core.port.sessao;

import br.com.sicredi.votacao.core.domain.SessaoDomain;
import br.com.sicredi.votacao.integration.datastore.entity.SessaoEntity;

import java.util.UUID;

public interface SessaoPersistenceOutbound {

    SessaoEntity abrir(SessaoDomain domain);
    SessaoEntity buscarPorId(UUID id);

    void atualizar(SessaoEntity entity);
}
