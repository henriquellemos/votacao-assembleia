package br.com.sicredi.votacao.core.exception;

public class ServicoException extends RuntimeException {

    public ServicoException(String mensagem){
        super(mensagem);
    }
}
