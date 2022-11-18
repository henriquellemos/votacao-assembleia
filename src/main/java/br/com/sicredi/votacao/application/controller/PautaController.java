package br.com.sicredi.votacao.application.controller;

import br.com.sicredi.votacao.application.dto.request.PautaRequest;
import br.com.sicredi.votacao.application.dto.response.PautaResponse;
import br.com.sicredi.votacao.core.port.PautaPort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/pautas")
@Api(tags = {"API de Votacao"})
@Slf4j
@RequiredArgsConstructor
public class PautaController {

    private final PautaPort pautaPort;

    @ApiOperation(value = "Cadastro de nova pauta")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pauta criada"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    @PostMapping
    public ResponseEntity<PautaResponse> cadastrarPauta(@RequestBody PautaRequest pautaRequest){
        var pautaResponse = pautaPort.cadastrar(pautaRequest);

        pautaResponse.setId(UUID.randomUUID());

        log.info("Log Test");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pautaResponse);
    }

}
