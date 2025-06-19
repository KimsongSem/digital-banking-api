package com.kimsong.digital_banking.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleResourceNotFound(NotFoundException ex) {
        log.info("Error: {}", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(String.format("ResourceNotFound Error: %s", ex.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DuplicatedException.class)
    protected ResponseEntity<ErrorResponse> handleResourceNotFound(DuplicatedException ex) {
        log.info("Error: {}", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(String.format("Resource Duplicated Error: %s", ex.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ErrorResponse> handleValidationError(ValidationException ex) {
        log.info("Error: {}", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(String.format("Validation Error: %s", ex.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<ErrorResponse> handleDatabaseError(DatabaseException ex) {
        log.info("Error: {}", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage(String.format("Database Error: %s", ex.getMessage()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponse> handleMistyped(NumberFormatException ex) {
        log.info("Error: {}", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(String.format("Mistyped Error: %s", ex.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("Error: {}", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(s -> errors.put(s.getField(), s.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(s -> errors.put(s.getObjectName(), s.getDefaultMessage()));

        ErrorValidResponse<Object> errorValidResponse = new ErrorValidResponse<>();
        errorValidResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorValidResponse.setMessage("Validation Error");
        errorValidResponse.setError(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorValidResponse);
    }

    /*@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("Error: {}", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(s -> errors.put(s.getField(), s.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(s -> errors.put(s.getObjectName(), s.getDefaultMessage()));

        ErrorValidResponse<Object> errorValidResponse = new ErrorValidResponse<>();
        errorValidResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorValidResponse.setMessage("Validation Error");
        errorValidResponse.setError(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorValidResponse);
    }*/

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeError(Exception ex) {
        log.info("Error: {}", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage(String.format("Runtime Error: %s", ex.getMessage()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAnythingElse(Exception ex) {
        log.info("Error: {}", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage(String.format("Error: %s", ex.getMessage()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}