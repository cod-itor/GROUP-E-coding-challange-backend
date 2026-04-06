package com.example.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateUser(DuplicateUserException e, HttpServletRequest request) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("type", "about:blank");
        error.put("title", "Conflict");
        error.put("status", 409);
        error.put("detail", e.getMessage());
        error.put("instance", request.getRequestURI());
        error.put("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException b, HttpServletRequest request) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("type", "about:blank");
        error.put("title", "Bad Request");
        error.put("status", 400);
        error.put("instance", request.getRequestURI());

        if (b.getErrors() != null && !b.getErrors().isEmpty()) {
            error.put("errors", b.getErrors());
        } else {
            error.put("detail", b.getMessage());
        }

        error.put("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
