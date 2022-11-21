package br.com.sicredi.votacao.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessaoResponse {

    private UUID id;

    @JsonProperty("id_pauta")
    private UUID idPauta;

    @JsonProperty("sessao_status")
    private String sessaoStatus;

    @JsonProperty("data_expiracao")
    private LocalDateTime dataExpiracao;
}
