package br.com.sicredi.votacao.integration.datastore;

import br.com.sicredi.votacao.core.domain.PautaDomain;
import br.com.sicredi.votacao.core.port.pauta.PautaOutbound;
import br.com.sicredi.votacao.integration.datastore.repository.PautaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static br.com.sicredi.votacao.integration.datastore.mapper.PautaMapper.paraEntity;

@Service
public class PautaPersistence implements PautaOutbound {

    private final PautaRepository pautaRepository;

    public PautaPersistence(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    @Override
    public Optional<UUID> cadastrar(PautaDomain pautaDomain) {
        return Optional.ofNullable(pautaRepository.save(paraEntity(pautaDomain)).getId());
    }
}
