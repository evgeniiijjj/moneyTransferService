package com.example.backend.advice;

import com.example.backend.dto.ResponseFailed;
import com.example.backend.exception.InternalServerErrorExceptionId1;
import com.example.backend.exception.InternalServerErrorExceptionId2;
import com.example.backend.exception.RejectedOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(RejectedOperationException.class)
    public ResponseEntity<String> rejectedOperationExceptionHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorExceptionId1.class)
    public ResponseEntity<String> internalServerErrorExceptionId1Handler(Exception e) {
        return new ResponseEntity<>(new ResponseFailed(e.getMessage(), 1).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternalServerErrorExceptionId2.class)
    public ResponseEntity<String> internalServerErrorExceptionId2Handler(Exception e) {
        return new ResponseEntity<>(new ResponseFailed(e.getMessage(), 2).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
