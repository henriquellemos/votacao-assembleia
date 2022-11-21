package br.com.sicredi.votacao.core.port.sessao;

import br.com.sicredi.votacao.core.domain.SessaoDomain;

public interface SessaoQueueOutbound {

    void notificar(SessaoDomain domain);
}
