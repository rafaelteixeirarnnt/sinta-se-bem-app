package br.com.leaf.sintasebemapp.application.exception;

public class EstabelecimentoValidationException extends RuntimeException {

    public EstabelecimentoValidationException(String message) {
        super(message);
    }

    public EstabelecimentoValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
