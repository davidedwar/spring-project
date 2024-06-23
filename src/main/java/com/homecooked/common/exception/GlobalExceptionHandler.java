package com.homecooked.common.exception;

import com.homecooked.common.response.GenericResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return GenericResponse.error(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).toResponseEntity();
    }

    @ExceptionHandler(EntityDeletedException.class)
    public ResponseEntity<?> handleEntityDeletedException(EntityDeletedException ex) {
        return GenericResponse.error(ex.getMessage(), HttpStatus.GONE).toResponseEntity();
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<?> handleNoContentException(EntityDeletedException ex) {
        return GenericResponse.error(ex.getMessage(), HttpStatus.NO_CONTENT).toResponseEntity();
    }

    @ExceptionHandler(VerificationException.class)
    public ResponseEntity<?> handleVerificationException(VerificationException ex) {
        return GenericResponse.error(ex.getMessage(), HttpStatus.UNAUTHORIZED).toResponseEntity();
    }

    @ExceptionHandler(CartChefClashException.class)
    public ResponseEntity<?> handleCartChefClashException(CartChefClashException ex) {
        return GenericResponse.error(ex.getMessage(), HttpStatus.FORBIDDEN).toResponseEntity();
    }
}
