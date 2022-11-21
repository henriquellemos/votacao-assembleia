package br.com.sicredi.votacao.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("total_votos")
    private Long totalVotos;

    @JsonProperty("total_sim")
    private Long totalSim;

    @JsonProperty("total_nao")
    private Long totalNao;
}
