package br.com.amain.backend.exception;
public class DadosInvalidosException extends RuntimeException{

    public DadosInvalidosException(String message) {
        super(message);
    }
}
