package br.com.sicredi.votacao.application.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AssociadoRequest {

    @NotBlank(message = "Campo não pode ser nulo/branco")
    private String nome;

    @NotNull(message = "Campo não pode ser nulo")
    private Long cpf;
}
