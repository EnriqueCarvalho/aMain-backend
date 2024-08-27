package br.com.amain.backend.exception;

public class SQLInsertException extends RuntimeException{

    public SQLInsertException(String message){
        super(message);
    }
}
