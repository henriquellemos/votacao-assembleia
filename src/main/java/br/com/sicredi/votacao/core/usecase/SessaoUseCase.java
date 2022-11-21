package br.com.sicredi.votacao.core.usecase;

import br.com.sicredi.votacao.application.dto.request.SessaoRequest;
import br.com.sicredi.votacao.application.dto.response.SessaoResponse;
import br.com.sicredi.votacao.core.port.pauta.PautaOutbound;
import br.com.sicredi.votacao.core.port.sessao.SessaoInbound;
import br.com.sicredi.votacao.core.port.sessao.SessaoPersistenceOutbound;
import br.com.sicredi.votacao.core.port.sessao.SessaoQueueOutbound;
import br.com.sicredi.votacao.core.port.voto.VotoOutbound;
import br.com.sicredi.votacao.integration.datastore.entity.SessaoEntity;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.OpcaoVoto;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.SessaoStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static br.com.sicredi.votacao.core.mapper.PautaMapper.paraDomain;
import static br.com.sicredi.votacao.core.mapper.SessaoMapper.paraIntegration;
import static br.com.sicredi.votacao.core.mapper.SessaoMapper.paraResponse;

@Service
@Slf4j
public class SessaoUseCase implements SessaoInbound {

    private final SessaoPersistenceOutbound sessaoPersistenceOutbound;
    private final SessaoQueueOutbound sessaoQueueOutbound;
    private final PautaOutbound pautaOutbound;
    private final VotoOutbound votoOutbound;

    public SessaoUseCase(SessaoPersistenceOutbound sessaoPersistenceOutbound, SessaoQueueOutbound sessaoQueueOutbound, PautaOutbound pautaOutbound, VotoOutbound votoOutbound) {
        this.sessaoPersistenceOutbound = sessaoPersistenceOutbound;
        this.sessaoQueueOutbound = sessaoQueueOutbound;
        this.pautaOutbound = pautaOutbound;
        this.votoOutbound = votoOutbound;
    }

    @Override
    public SessaoResponse abrir(SessaoRequest request, UUID idPauta) {
        var pauta = paraDomain(this.pautaOutbound.buscarPorId(idPauta));

        var sessaoDomain = paraIntegration(request, pauta);

        var sessaoEntity = this.sessaoPersistenceOutbound.abrir(sessaoDomain);

        log.info("Início do processo que contabilizará os votos.");
        try {
            new ScheduledThreadPoolExecutor(10)
                    .schedule(() -> fechar(sessaoEntity), sessaoDomain.getTempoSessao(), TimeUnit.MINUTES);
        } catch (Exception e){
            log.error("Erro ao settar scheduler.");
        }

        return paraResponse(sessaoEntity);
    }

    @Transactional
    public void fechar(SessaoEntity sessaoEntity) {

        var pautaDomain = paraDomain(this.pautaOutbound.buscarPorId(sessaoEntity.getPauta().getId()));
        var quantidadeVotoSim = this.votoOutbound.contabilizar(pautaDomain, OpcaoVoto.SIM);
        var quantidadeVotoNao = this.votoOutbound.contabilizar(pautaDomain, OpcaoVoto.NAO);

        log.info("Quantidade sim - {}", quantidadeVotoSim);
        log.info("Quantidade não - {}", quantidadeVotoNao);

        if(sessaoEntity.getPauta() != null) {
            sessaoEntity.getPauta().setTotalSim(quantidadeVotoSim);
            sessaoEntity.getPauta().setTotalNao(quantidadeVotoNao);
            sessaoEntity.getPauta().setTotalVotos(Long.sum(quantidadeVotoSim, quantidadeVotoNao));
            sessaoEntity.getPauta().setVotada(Boolean.TRUE);
        }

        sessaoEntity.setSessaoStatus(SessaoStatus.CONCLUIDA);
        this.sessaoPersistenceOutbound.atualizar(sessaoEntity);

        var pautaAtualizada = this.pautaOutbound.atualizar(sessaoEntity.getPauta());

        log.info("Pauta '{}' atualizada.", pautaAtualizada.getId());

        //this.sessaoQueueOutbound.notificar();

        log.info("Fim do processo que contabilizou os votos.");
    }
}
