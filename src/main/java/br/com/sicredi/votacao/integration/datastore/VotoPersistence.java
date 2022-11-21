package br.com.sicredi.votacao.integration.datastore;

import br.com.sicredi.votacao.core.domain.PautaDomain;
import br.com.sicredi.votacao.core.port.voto.VotoOutbound;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import br.com.sicredi.votacao.integration.datastore.entity.VotoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.OpcaoVoto;
import br.com.sicredi.votacao.integration.datastore.mapper.PautaMapper;
import br.com.sicredi.votacao.integration.datastore.repository.VotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.sicredi.votacao.integration.datastore.mapper.VotoMapper.paraEntity;

@Slf4j
@Service
public class VotoPersistence implements VotoOutbound {

    private final VotoRepository votoRepository;

    public VotoPersistence(VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    @Override
    public VotoEntity salvar(OpcaoVoto voto, PautaEntity pauta) {
        return this.votoRepository.save(paraEntity(voto, pauta));
    }

    @Override
    public Long contabilizar(PautaDomain domain, OpcaoVoto opcao) {
        return this.votoRepository.countByPautaAndVoto(PautaMapper.paraEntity(domain), opcao);
    }
}
