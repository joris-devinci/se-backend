package com.example.demo.controllers;

import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;

@RestControllerAdvice(assignableTypes=AuthController.class)
public class AuthErrors {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> invalid(MethodArgumentNotValidException error) {
        var fields=error.getBindingResult().getFieldErrors().stream().map(field ->
            Map.of("field",field.getField(),"message",field.getDefaultMessage())).toList();
        return ResponseEntity.badRequest().body(Map.of("detail","Please check the required fields.","errors",fields));
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> status(ResponseStatusException error) {
        return ResponseEntity.status(error.getStatusCode()).body(Map.of("detail",error.getReason()));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> duplicate() {
        return ResponseEntity.status(409).body(Map.of("detail","Account conflicts with existing data."));
    }
}
