package br.com.leaf.sintasebemapp.application.exception;

public class UsuarioNegocioValidationException extends RuntimeException {

    public UsuarioNegocioValidationException(String message) {
        super(message);
    }

    public UsuarioNegocioValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
