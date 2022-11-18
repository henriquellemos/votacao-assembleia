package br.com.sicredi.votacao.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PautaResponse {

    private UUID id;
    private String nome;
    private String descricao;
    private Boolean votada;
}
