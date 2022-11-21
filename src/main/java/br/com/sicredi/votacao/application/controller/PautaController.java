package br.com.sicredi.votacao.application.controller;

import br.com.sicredi.votacao.application.dto.request.PautaRequest;
import br.com.sicredi.votacao.application.dto.request.SessaoRequest;
import br.com.sicredi.votacao.application.dto.request.VotacaoRequest;
import br.com.sicredi.votacao.application.dto.response.PautaResponse;
import br.com.sicredi.votacao.application.dto.response.SessaoResponse;
import br.com.sicredi.votacao.application.dto.response.VotacaoResponse;
import br.com.sicredi.votacao.core.port.pauta.PautaInbound;
import br.com.sicredi.votacao.core.port.sessao.SessaoInbound;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/pautas")
@Api(tags = {"API de Votacao"})
@Slf4j
@RequiredArgsConstructor
public class PautaController {

    private final PautaInbound pautaInbound;

    private final SessaoInbound sessaoInbound;

    @ApiOperation(value = "Cadastro de nova pauta")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pauta criada"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    @PostMapping
    public ResponseEntity<PautaResponse> cadastrarPauta(@RequestBody @Valid PautaRequest pautaRequest){
        log.info("Request recebido: {}", pautaRequest);
        var pautaResponse = PautaResponse.builder()
                .id(this.pautaInbound.cadastrar(pautaRequest).orElseThrow())
                .build();

        log.info("Pauta criada com id '{}'", pautaResponse.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pautaResponse);
    }

    @ApiOperation(value = "Cadastro de nova sessao para votacao")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sessao criada"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    @PostMapping("{id}/sessoes")
    public ResponseEntity<SessaoResponse> abrirSessao(@PathVariable("id") UUID idPauta, @Valid @RequestBody SessaoRequest sessaoRequest){
        log.info("Abertura de sessão para votação da pauta '{}'", idPauta);

        var sessaoResponse = this.sessaoInbound.abrir(sessaoRequest, idPauta);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sessaoResponse);
    }

    @ApiOperation(value = "Cadastro de nova sessao para votacao")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sessao criada"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    @PostMapping("{id_pauta}/sessoes/{id_sessao}/associados/{cpf}/votar")
    public ResponseEntity<VotacaoResponse> votar(@PathVariable("id_pauta") UUID idPauta,
                                                 @PathVariable("id_sessao") UUID idSessao,
                                                 @PathVariable("cpf") Long cpfAssociado,
                                                 @Valid @RequestBody VotacaoRequest votacaoRequest){

        log.info("Votação do associado '{}' para pauta '{}'", cpfAssociado, idPauta);

        var votacaoResponse = this.pautaInbound.votar(idPauta, idSessao, cpfAssociado, votacaoRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(votacaoResponse);
    }
}
