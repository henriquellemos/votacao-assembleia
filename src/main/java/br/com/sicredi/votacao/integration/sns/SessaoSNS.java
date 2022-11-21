package br.com.sicredi.votacao.integration.sns;

import br.com.sicredi.votacao.core.domain.SessaoDomain;
import br.com.sicredi.votacao.core.exception.ServicoException;
import br.com.sicredi.votacao.core.port.sessao.SessaoQueueOutbound;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SessaoSNS implements SessaoQueueOutbound {

    private AmazonSNS amazonSNS;

    @Value("${aws.sns.topic.votacao.arn}")
    private String snsTopicArn;

    public SessaoSNS(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    @Override
    public void notificar(SessaoDomain domain) {
        log.info("Iniciando publish SNS");

        try {
            var payload = geradorPayload(domain);
            PublishRequest request = new PublishRequest();
            request.setMessage(payload);
            request.setTopicArn(snsTopicArn);

            PublishResult resultado = this.amazonSNS.publish(request);
            log.info("Publish na SNS efetuada com sucesso: '{}'", resultado.getSdkHttpMetadata().toString());

        } catch (AmazonSNSException ex) {
            log.info("Erro ao efetuar Publish na SNS: '{}'", ex.getErrorMessage(), ex);
            throw new ServicoException("Erro ao efetuar Publish na SNS para sessão '"+ domain.getId() +"'.");
        }
    }

    private String geradorPayload(SessaoDomain domain) {

        var payload = new JSONObject();

        payload.put("total_votos_sim", domain.getPauta().getQuantidadeVotoSim());
        payload.put("total_votos_nao", domain.getPauta().getQuantidadeVotoNao());
        payload.put("total_votos", domain.getPauta().getTotalVotos());
        payload.put("id_pauta", domain.getPauta().getId());
        payload.put("id_sessao", domain.getId());

        return payload.toString();
    }
}
