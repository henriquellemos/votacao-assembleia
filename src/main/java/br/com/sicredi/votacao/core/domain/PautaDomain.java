package br.com.sicredi.votacao.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDomain {

    private UUID id;
    private String nome;
    private String descricao;
    private Boolean votada;
    private SessaoDomain sessaoDomain;
    private Long quantidadeVotoSim;
    private Long quantidadeVotoNao;
    private Long totalVotos;
}
