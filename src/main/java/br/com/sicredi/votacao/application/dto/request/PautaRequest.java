package br.com.sicredi.votacao.application.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PautaRequest {

    @NotNull(message = "Campo deve ser preenchido")
    private String nome;
    private String descricao;
}
