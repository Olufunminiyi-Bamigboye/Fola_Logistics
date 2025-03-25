package com.wayup.Fola_Logistics.advice;

import com.wayup.Fola_Logistics.dto.response.ErrorDetail;
import com.wayup.Fola_Logistics.exception.ExistingEmailException;
import com.wayup.Fola_Logistics.exception.InvalidAmountException;
import com.wayup.Fola_Logistics.exception.InvalidRequestException;
import com.wayup.Fola_Logistics.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(ex.getMessage(), LocalDateTime.now(), request.getDescription(false));

        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorDetail> handleInvalidUserException(InvalidRequestException ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(ex.getMessage(), LocalDateTime.now(), request.getDescription(false));

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ExistingEmailException.class)
    public ResponseEntity<ErrorDetail> handleInvalidUserException(ExistingEmailException ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(ex.getMessage(), LocalDateTime.now(), request.getDescription(false));

        return new ResponseEntity<>(errorDetail, HttpStatus.IM_USED);

    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ErrorDetail> handleInvalidAmount(ExistingEmailException ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(ex.getMessage(), LocalDateTime.now(), request.getDescription(false));

        return new ResponseEntity<>(errorDetail, HttpStatus.IM_USED);

    }
}
