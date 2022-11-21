package br.com.sicredi.votacao.application.config.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MensagemErro {

    private String codigo;

    private String mensagem;

    private List<Campo> campos;

    @Data
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Campo {
        private String nome;

        private String mensagem;

        private String valor;
    }
}
