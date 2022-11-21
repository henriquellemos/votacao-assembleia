package br.com.sicredi.votacao.core.exception;

public class AssociadoJaVotouException extends RuntimeException {

    public AssociadoJaVotouException(String mensagem){
        super(mensagem);
    }
}
