package br.com.sicredi.votacao.core.domain;

import br.com.sicredi.votacao.integration.datastore.entity.enumerator.StatusVoto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoDomain {

    private Long cpf;
    private String nome;
    private StatusVoto status;
}
