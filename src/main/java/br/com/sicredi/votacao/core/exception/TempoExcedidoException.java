package br.com.sicredi.votacao.core.exception;

public class TempoExcedidoException extends RuntimeException {

    public TempoExcedidoException(String mensagem){
        super(mensagem);
    }
}
