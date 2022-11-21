package br.com.sicredi.votacao.application.config.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Problema {

    private HttpStatus status;

    private String tipo;

    private String titulo;

    private String detalhe;

    @JsonProperty("mensagem_usuario")
    private String mensagemUsuario;

    private List<Objeto> objetos;

    @Getter
    @Builder
    public static class Objeto {
        private String nome;

        @JsonProperty("mensagem_usuario")
        private String mensagemUsuario;
    }
}
