package br.com.amain.backend.exception;

public class AcessoInvalidoException extends RuntimeException{
    
    public AcessoInvalidoException(String message) {
        super(message);
    }
}
