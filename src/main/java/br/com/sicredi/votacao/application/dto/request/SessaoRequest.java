package br.com.sicredi.votacao.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessaoRequest {

    @Min(value = 1, message = "Tempo deve ser maior que ZERO")
    @JsonProperty("tempo_sessao")
    private Long tempoSessao;
}
