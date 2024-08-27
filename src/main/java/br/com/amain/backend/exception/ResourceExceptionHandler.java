package br.com.amain.backend.exception;

import java.time.LocalDateTime;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
    

    
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError>objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {     

        StandardError error =
                new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),ex.getMessage() , request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadParamsException.class)
    public ResponseEntity<StandardError>badParams(BadParamsException ex, HttpServletRequest request) {        
      
        StandardError error =
                new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),ex.getMessage(), request.getRequestURI());
          
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DadosInvalidosException.class)
    public ResponseEntity<StandardError>dadosInvalidos(DadosInvalidosException ex, HttpServletRequest request) {        
      
        StandardError error =
                new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),ex.getMessage(), request.getRequestURI());
          
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(SQLInsertException.class)
    public ResponseEntity<StandardError>sqlInsert(SQLInsertException ex, HttpServletRequest request) {        
      
        StandardError error =
                new StandardError(LocalDateTime.now(), HttpStatus.CONFLICT.value(),ex.getMessage(), request.getRequestURI());
          
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}
