package br.com.sicredi.votacao.integration.datastore;

import br.com.sicredi.votacao.core.domain.SessaoDomain;
import br.com.sicredi.votacao.core.exception.RecursoNaoEncontradoException;
import br.com.sicredi.votacao.core.port.sessao.SessaoPersistenceOutbound;
import br.com.sicredi.votacao.integration.datastore.entity.SessaoEntity;
import br.com.sicredi.votacao.integration.datastore.repository.PautaRepository;
import br.com.sicredi.votacao.integration.datastore.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.sicredi.votacao.integration.datastore.mapper.SessaoMapper.paraEntity;

@Service
public class SessaoPersistence implements SessaoPersistenceOutbound {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;

    public SessaoPersistence(SessaoRepository sessaoRepository, PautaRepository pautaRepository) {
        this.sessaoRepository = sessaoRepository;
        this.pautaRepository = pautaRepository;
    }

    @Override
    public SessaoEntity abrir(SessaoDomain domain) {
        var pautaEntity = this.pautaRepository.findById(domain.getPauta().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pauta '" + domain.getPauta().getId() + "' não encontrada."));
        return this.sessaoRepository.save(paraEntity(domain, pautaEntity));
    }

    @Override
    public SessaoEntity buscarPorId(UUID id) {
        return this.sessaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sessão com id: '" + id + "' não encontrada"));
    }

    @Override
    public void atualizar(SessaoEntity sessaoEntity) {
        this.sessaoRepository.save(sessaoEntity);
    }
}
