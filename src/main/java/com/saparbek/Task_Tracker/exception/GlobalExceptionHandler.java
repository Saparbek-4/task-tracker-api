package com.saparbek.Task_Tracker.exception;

import com.saparbek.Task_Tracker.dto.ErrorResponse;
import com.saparbek.Task_Tracker.dto.ValidationErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ValidationErrorResponse> errors = fieldErrors.stream()
                .map(error -> new ValidationErrorResponse(error.getField(), error.getDefaultMessage(), HttpStatus.BAD_REQUEST.value()))
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleInvalidEnum(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleInvalidID(EntityNotFoundException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

}
