package br.com.leaf.sintasebemapp.infra.exception;

import br.com.leaf.sintasebemapp.application.exception.NegocioException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ErrorResponse> handleNegocioException(NegocioException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String log = extractLogSnippet(ex);
        LocalDateTime timestamp = LocalDateTime.now();
        String errorCode = "BUSINESS_ERROR";
        String path = request.getRequestURI();

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), status.value(), log, timestamp, errorCode, path);
        return new ResponseEntity<>(errorResponse, status);
    }

    private String extractLogSnippet(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString().split("\n")[0];
    }



}
