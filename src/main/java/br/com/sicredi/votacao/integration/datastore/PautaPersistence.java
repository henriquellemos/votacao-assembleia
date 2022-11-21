package br.com.sicredi.votacao.integration.datastore;

import br.com.sicredi.votacao.core.domain.PautaDomain;
import br.com.sicredi.votacao.core.exception.RecursoNaoEncontradoException;
import br.com.sicredi.votacao.core.port.pauta.PautaOutbound;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import br.com.sicredi.votacao.integration.datastore.repository.PautaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static br.com.sicredi.votacao.integration.datastore.mapper.PautaMapper.paraEntity;

@Slf4j
@Service
public class PautaPersistence implements PautaOutbound {

    private final PautaRepository pautaRepository;

    public PautaPersistence(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    @Override
    public Optional<UUID> cadastrar(PautaDomain pautaDomain) {
        log.info("Cadastrando pauta '{}'.", pautaDomain.getNome());
        return Optional.ofNullable(pautaRepository.save(paraEntity(pautaDomain)).getId());
    }

    @Override
    @Transactional
    public PautaEntity buscarPorId(UUID id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("pauta com id: '" + id + "' não encontrada"));
    }

    @Override
    public PautaEntity atualizar(PautaEntity pautaEntity) {
        return this.pautaRepository.save(pautaEntity);
    }
}
