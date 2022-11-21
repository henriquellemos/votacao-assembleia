package br.com.sicredi.votacao.core.exception;

public class SessaoNaoConcluidaException extends RuntimeException {

    public SessaoNaoConcluidaException(String mensagem, Exception ex){
        super(mensagem, ex);
    }
}
