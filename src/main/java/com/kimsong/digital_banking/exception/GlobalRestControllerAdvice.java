package com.kimsong.digital_banking.exception;

import com.kimsong.digital_banking.exception.response.ErrorResponseDetail;
import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error(ex.getMessage(), ex);

        ErrorResponseDetail<Object> errorResponseDetail = getErrorResponseDetail(ex.getStatusEnum(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDetail);
    }

    @ExceptionHandler(ResourceDuplicatedException.class)
    protected ResponseEntity<Object> handleResourceDuplicated(ResourceDuplicatedException ex) {
        logger.error(ex.getMessage(), ex);
        
        ErrorResponseDetail<Object> errorResponseDetail = getErrorResponseDetail(ex.getStatusEnum(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDetail);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidation(ValidationException ex) {
        logger.error(ex.getMessage(), ex);

        ErrorResponseDetail<Object> errorResponseDetail = getErrorResponseDetail(ex.getStatusEnum(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDetail);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        logger.error(ex.getMessage(), ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(s -> errors.put(s.getField(), s.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(s -> errors.put(s.getObjectName(), s.getDefaultMessage()));

        ErrorResponseDetail<Object> errorResponseDetail = getErrorResponseDetail(ErrorStatusEnum.BAD_REQUEST, errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDetail);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeError(RuntimeException ex) {
        logger.error(ex.getMessage(), ex);

        ErrorResponseDetail<Object> errorResponseDetail = getErrorResponseDetail(ErrorStatusEnum.INTERNAL_SERVER_ERROR, null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAnythingElse(Exception ex) {
        logger.error(ex.getMessage(), ex);

        ErrorResponseDetail<Object> errorResponseDetail = getErrorResponseDetail(ErrorStatusEnum.INTERNAL_SERVER_ERROR, null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDetail);
    }

    private ErrorResponseDetail<Object> getErrorResponseDetail(ErrorStatusEnum statusEnum, Object details) {
        ErrorResponseDetail<Object> responseException = new ErrorResponseDetail<>();
        responseException.setStatus(statusEnum.status);
        responseException.setMessage(statusEnum.message);
        responseException.setData(details);
        return responseException;
    }

}