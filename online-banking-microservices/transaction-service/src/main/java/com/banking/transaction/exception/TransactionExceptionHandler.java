package com.banking.transaction.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    
    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(FeignException.BadRequest ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "TRANSACTION_FAILED",
                        "message", ex.contentUTF8()
                ));
    }
    

//    @ExceptionHandler(FeignException.BadRequest.class)
//    public ResponseEntity<ErrorResponse> handleBadRequest(FeignException.BadRequest ex) {
//
//        try {
//            ErrorResponse error = objectMapper.readValue(ex.contentUTF8(), ErrorResponse.class);
//            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//
//        } catch (Exception e) {
//
//            ErrorResponse error = new ErrorResponse();
//            error.setError("TRANSACTION_FAILED");
//            error.setMessage("Transaction failed");
//
//            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//        }
//    }
}