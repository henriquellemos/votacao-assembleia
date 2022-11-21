package br.com.sicredi.votacao.core.usecase;

import br.com.sicredi.votacao.application.dto.request.PautaRequest;
import br.com.sicredi.votacao.application.dto.request.VotacaoRequest;
import br.com.sicredi.votacao.application.dto.response.VotacaoResponse;
import br.com.sicredi.votacao.core.exception.AssociacaoDesabilitadoParaVotoException;
import br.com.sicredi.votacao.core.port.associado.AssociadoOutbound;
import br.com.sicredi.votacao.core.port.pauta.PautaInbound;
import br.com.sicredi.votacao.core.port.pauta.PautaOutbound;
import br.com.sicredi.votacao.core.port.sessao.SessaoPersistenceOutbound;
import br.com.sicredi.votacao.core.port.voto.RegistroVotoOutbound;
import br.com.sicredi.votacao.integration.datastore.entity.AssociadoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.PautaEntity;
import br.com.sicredi.votacao.integration.datastore.entity.SessaoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.OpcaoVoto;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.StatusVoto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static br.com.sicredi.votacao.core.mapper.PautaMapper.paraIntegration;

@Slf4j
@Service
public class PautaUseCase implements PautaInbound {

    private final PautaOutbound pautaOutbound;
    private final SessaoPersistenceOutbound sessaoPersistenceOutbound;
    private final AssociadoOutbound associadoOutbound;
    private final RegistroVotoOutbound registroVotoOutbound;

    public PautaUseCase(PautaOutbound pautaOutbound,
                        SessaoPersistenceOutbound sessaoPersistenceOutbound,
                        AssociadoOutbound associadoOutbound,
                        RegistroVotoOutbound registroVotoOutbound
    ) {
        this.pautaOutbound = pautaOutbound;
        this.sessaoPersistenceOutbound = sessaoPersistenceOutbound;
        this.associadoOutbound = associadoOutbound;
        this.registroVotoOutbound = registroVotoOutbound;
    }

    @Override
    public Optional<UUID> cadastrar(PautaRequest request) {
        return pautaOutbound.cadastrar(paraIntegration(request));
    }

    @Override
    public VotacaoResponse votar(UUID idPauta, UUID idSessao, Long cpfAssociado, VotacaoRequest request) {

        var pauta = this.pautaOutbound.buscarPorId(idPauta);
        var sessao = this.sessaoPersistenceOutbound.buscarPorId(idSessao);
        var associado = this.associadoOutbound.buscarPorId(cpfAssociado);
        if(associado.getStatus().equals(StatusVoto.DESABILITADO_PARA_VOTO)){
            throw new AssociacaoDesabilitadoParaVotoException("Associado com CPF '" + associado.getCpf() + "' não pode votar.");
        }
        var voto = OpcaoVoto.transformarOpcao(request.getVoto());

        var votoEfetivado = efetuarVoto(pauta, sessao, associado, voto);

        if(votoEfetivado) {
            return VotacaoResponse.builder()
                    .status("Voto contabilizado")
                    .build();
        } else {
            return VotacaoResponse.builder()
                    .status("Voto NÃO contabilizado")
                    .detalhe("Associado '" + associado.getCpf() + "' já efetuou voto na pauta '" + pauta.getId() + "'.")
                    .build();
        }
    }

    private Boolean efetuarVoto(PautaEntity pauta, SessaoEntity sessao, AssociadoEntity associado, OpcaoVoto voto) {
        log.info("Efetuando voto para pauta '{}' do associado '{}'.", pauta.getId(), associado.getCpf());

        return this.registroVotoOutbound.efetuarVoto(pauta, sessao, associado, voto);
    }
}
