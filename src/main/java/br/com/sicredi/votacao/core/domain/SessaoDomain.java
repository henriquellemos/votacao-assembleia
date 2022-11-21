package br.com.sicredi.votacao.core.domain;

import br.com.sicredi.votacao.integration.datastore.entity.enumerator.SessaoStatus;
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
public class SessaoDomain {

    private UUID id;
    private PautaDomain pauta;
    private Long tempoSessao;
    private LocalDateTime dataExpiracao;
    private SessaoStatus sessaoStatus;
}
