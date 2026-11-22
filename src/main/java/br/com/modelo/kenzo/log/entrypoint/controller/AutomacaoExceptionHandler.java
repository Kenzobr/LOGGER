package br.com.modelo.kenzo.log.entrypoint.controller;

import br.com.modelo.kenzo.log.core.domain.AutomacaoNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AutomacaoExceptionHandler {

    @ExceptionHandler(AutomacaoNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> naoEncontrada(AutomacaoNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> requisicaoInvalida(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
    }
}
