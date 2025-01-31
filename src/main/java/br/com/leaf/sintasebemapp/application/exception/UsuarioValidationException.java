package br.com.leaf.sintasebemapp.application.exception;

public class UsuarioValidationException extends RuntimeException {

    public UsuarioValidationException(String message) {
        super(message);
    }

    public UsuarioValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
