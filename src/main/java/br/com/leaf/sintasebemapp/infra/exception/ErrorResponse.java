package br.com.leaf.sintasebemapp.infra.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private String message;
    private int status;
    private String log;
    private LocalDateTime timestamp;
    private String errorCode;
    private String path;

    public ErrorResponse(String message, int status, String log, LocalDateTime timestamp, String errorCode, String path) {
        this.message = message;
        this.status = status;
        this.log = log;
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.path = path;
    }
}
