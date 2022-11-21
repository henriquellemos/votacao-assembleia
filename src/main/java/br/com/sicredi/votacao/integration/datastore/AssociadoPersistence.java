package br.com.sicredi.votacao.integration.datastore;

import br.com.sicredi.votacao.core.domain.AssociadoDomain;
import br.com.sicredi.votacao.core.exception.RecursoNaoEncontradoException;
import br.com.sicredi.votacao.core.port.associado.AssociadoOutbound;
import br.com.sicredi.votacao.integration.datastore.entity.AssociadoEntity;
import br.com.sicredi.votacao.integration.datastore.repository.AssociadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.sicredi.votacao.integration.datastore.mapper.AssociadoMapper.paraEntity;

@Slf4j
@Service
public class AssociadoPersistence implements AssociadoOutbound {

    private final AssociadoRepository associadoRepository;

    public AssociadoPersistence(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    @Override
    public AssociadoEntity cadastrar(AssociadoDomain domain) {
        log.info("Salvando associado com CPF '{}'.", domain.getCpf());
        return this.associadoRepository.save(paraEntity(domain));
    }

    @Override
    public AssociadoEntity buscarPorId(Long cpfAssociado) {
        return this.associadoRepository.findById(cpfAssociado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Associado com CPF: '" + cpfAssociado + "' não encontrado."));
    }
}
