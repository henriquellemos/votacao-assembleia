package br.com.sicredi.votacao.integration.datastore;

import br.com.sicredi.votacao.core.exception.AssociadoJaVotouException;
import br.com.sicredi.votacao.core.port.voto.RegistroVotoOutbound;
import br.com.sicredi.votacao.core.port.voto.VotoOutbound;
import br.com.sicredi.votacao.integration.datastore.entity.AssociadoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import br.com.sicredi.votacao.integration.datastore.entity.SessaoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.OpcaoVoto;
import br.com.sicredi.votacao.integration.datastore.repository.RegistroVotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.sicredi.votacao.core.mapper.RegistroVotoMapper.paraEntity;

@Slf4j
@Service
public class RegistroVotoPersistence implements RegistroVotoOutbound {

    private final RegistroVotoRepository registroVotoRepository;
    private final VotoOutbound votoOutbound;

    public RegistroVotoPersistence(RegistroVotoRepository registroVotoRepository, VotoOutbound votoOutbound) {
        this.registroVotoRepository = registroVotoRepository;
        this.votoOutbound = votoOutbound;
    }

    @Override
    public Boolean efetuarVoto(PautaEntity pauta, SessaoEntity sessao, AssociadoEntity associado, OpcaoVoto voto) {
        if(this.registroVotoRepository.existsByAssociadoAndPautaVotada(associado, pauta)) {
            throw new AssociadoJaVotouException("Associado '" + associado.getCpf()
                    + "' já votou na pauta '" + pauta.getId() + "'.");
        }

        log.info("Salvando voto!");
        this.votoOutbound.salvar(voto, pauta);

        try {
            this.registroVotoRepository.save(paraEntity(pauta, associado));
            return true;
        } catch (Exception ex){
            log.error("Não foi possível concluir o voto para associado {} e pauta {}.", associado.getCpf(), pauta.getId());
            return false;
        }
    }
}
