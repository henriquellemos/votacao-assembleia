package br.com.sicredi.votacao.application.controller;

import br.com.sicredi.votacao.application.dto.request.AssociadoRequest;
import br.com.sicredi.votacao.application.dto.response.AssociadoResponse;
import br.com.sicredi.votacao.core.port.associado.AssociadoInbound;
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

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/associados")
@Api(tags = {"API de Votacao"})
@Slf4j
@RequiredArgsConstructor
public class AssociadoController {

    private final AssociadoInbound associadoInbound;

    @ApiOperation(value = "Cadastro de novo associado")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Associado criado"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "CPF Not Found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    @PostMapping
    public ResponseEntity<AssociadoResponse> cadastrar(@Valid @RequestBody AssociadoRequest request){
        var response = this.associadoInbound.cadastrar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
