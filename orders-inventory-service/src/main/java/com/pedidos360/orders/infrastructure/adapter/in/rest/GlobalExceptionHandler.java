package com.pedidos360.orders.infrastructure.adapter.in.rest;

import com.pedidos360.orders.domain.exception.DomainException;
import com.pedidos360.orders.domain.exception.InventoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Más específico primero: Spring lo prioriza sobre el genérico de abajo
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(InventoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    // Cubre InsufficientStockException e InvalidOrderStateException por herencia
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainConflict(DomainException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

    public record ErrorResponse(String message) {}
}