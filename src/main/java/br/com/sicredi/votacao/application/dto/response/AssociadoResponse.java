package br.com.sicredi.votacao.application.dto.response;

import br.com.sicredi.votacao.integration.datastore.entity.enumerator.StatusVoto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssociadoResponse {

    private String nome;

    private Long cpf;

    private StatusVoto status;
}
