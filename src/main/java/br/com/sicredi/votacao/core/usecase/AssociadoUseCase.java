package br.com.sicredi.votacao.core.usecase;

import br.com.sicredi.votacao.application.dto.request.AssociadoRequest;
import br.com.sicredi.votacao.application.dto.response.AssociadoResponse;
import br.com.sicredi.votacao.core.exception.RecursoNaoEncontradoException;
import br.com.sicredi.votacao.core.exception.ServicoException;
import br.com.sicredi.votacao.core.port.associado.AssociadoInbound;
import br.com.sicredi.votacao.core.port.associado.AssociadoOutbound;
import br.com.sicredi.votacao.integration.datastore.entity.enumerator.StatusVoto;
import br.com.sicredi.votacao.integration.feign.ValidadorCPFFeign;
import br.com.sicredi.votacao.integration.feign.response.UserInfoResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.sicredi.votacao.core.mapper.AssociadoMapper.paraIntegration;
import static br.com.sicredi.votacao.core.mapper.AssociadoMapper.paraResponse;

@Service
@Slf4j
public class AssociadoUseCase implements AssociadoInbound {

    private final AssociadoOutbound associadoOutbound;
    private final ValidadorCPFFeign validadorCPFFeign;

    public AssociadoUseCase(AssociadoOutbound associadoOutbound, ValidadorCPFFeign validadorCPFFeign) {
        this.associadoOutbound = associadoOutbound;
        this.validadorCPFFeign = validadorCPFFeign;
    }

    @Override
    public AssociadoResponse cadastrar(AssociadoRequest request) {
        UserInfoResponse userInfo;
        try {
            userInfo = this.validadorCPFFeign.getUserInfo(request.getCpf()).getBody();
        } catch (FeignException.NotFound fenf){
            log.error("Erro de feign client: CPF '" + request.getCpf() + "' não encontrado.");
            throw new RecursoNaoEncontradoException("CPF '" + request.getCpf() + "' não encontrado.");
        } catch (Exception e){
            log.error("Erro de feign client genérico");
            throw new ServicoException("Erro de feign client genérico");
        }

        if(userInfo != null) log.info("CPF '{}' encontrado.", request.getCpf());

        assert userInfo != null;
        var status = StatusVoto.transformar(userInfo.getStatus());
        return paraResponse(this.associadoOutbound.cadastrar(paraIntegration(request, status)));
    }
}
