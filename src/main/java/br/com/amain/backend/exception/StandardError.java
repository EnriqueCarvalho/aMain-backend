package br.com.amain.backend.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class StandardError {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
  
}

