package br.com.amain.backend.model;

public enum ColorsEnum {
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    public final String cor;

    private ColorsEnum(String cor){
        this.cor = cor;
    }
}

