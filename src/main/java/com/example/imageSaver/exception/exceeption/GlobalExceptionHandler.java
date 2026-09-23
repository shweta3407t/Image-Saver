package com.example.imageSaver.exception.exceeption;

import com.example.imageSaver.controllers.UploadImageController;
import com.example.imageSaver.dto.ExceptionResponseDTO;
import com.example.imageSaver.dto.ValidateExceptionResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    public UploadImageController imageFileUploadController;


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException exception) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                LocalDateTime.now(),
                status.value() ,
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDTO);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ExceptionResponseDTO> handleDuplicateResourceException(HttpServletRequest request, DuplicateResourceException exception) {

        HttpStatus status = HttpStatus.CONFLICT;

        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                LocalDateTime.now(),
                status.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDTO);
    }








    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDTO> handleRuntimeException(HttpServletRequest request, RuntimeException exception) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;


        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                LocalDateTime.now(),
                status.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleDuplicateResourceException(HttpServletRequest request, Exception exception) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;


        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                LocalDateTime.now(),
                 status.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseDTO);
    }



















    @ExceptionHandler(MethodArgumentNotValidException.class)
    public   ResponseEntity<ValidateExceptionResponseDTO> handleMethodArgumentNotValidException(
            HttpServletRequest request,MethodArgumentNotValidException exception){

        Map<String , String> fieldError=new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> fieldError.put(error.getField() , error.getDefaultMessage()));


        HttpStatus status = HttpStatus.NOT_FOUND;


        ValidateExceptionResponseDTO validateExceptionResponseDTO=new ValidateExceptionResponseDTO(
                LocalDateTime.now(),
                 status.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                fieldError
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validateExceptionResponseDTO);
    }

}
