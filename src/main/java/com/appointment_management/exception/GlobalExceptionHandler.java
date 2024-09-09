package com.appointment_management.exception;

import com.appointment_management.dto.ErrorResponseDTO;
import com.appointment_management.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO("404", ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsWithEmailException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsWithEmailException(UserAlreadyExistsWithEmailException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred.");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
