/*
* Classe responsável por capturar todas as exceções que ocorrerem no controller.
*
*/

package br.com.douglas.turingbankh2.controllers.exceptions;

import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
import br.com.douglas.turingbankh2.services.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)    // Poderia ser direto um RuntimeException
    public ResponseEntity<StandartError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        StandartError err = new StandartError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setError("Recurso não encontrado");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(EntityDuplicatedException.class)    // Poderia ser direto um RuntimeException
    public ResponseEntity<StandartError> entityDuplicated(EntityDuplicatedException e, HttpServletRequest request){
        StandartError err = new StandartError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.CONFLICT.value());
        err.setError("Recurso já existe na base de dados");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

}
