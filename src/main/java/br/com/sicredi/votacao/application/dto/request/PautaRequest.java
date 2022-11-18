package br.com.sicredi.votacao.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PautaRequest {

    private String nome;
    private String descricao;
}
