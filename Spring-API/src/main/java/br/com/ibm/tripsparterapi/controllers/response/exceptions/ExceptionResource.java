package br.com.ibm.tripsparterapi.controllers.response.exceptions;

import br.com.ibm.tripsparterapi.services.exceptions.EmailException;
import br.com.ibm.tripsparterapi.services.exceptions.TripException;
import br.com.ibm.tripsparterapi.services.exceptions.FavoriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionResource {
    @ExceptionHandler(EmailException.class)
    public ResponseEntity<PatternErrors> error(EmailException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        PatternErrors patternErrors = new PatternErrors(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(patternErrors);

    }

    @ExceptionHandler(TripException.class)
    public ResponseEntity<PatternErrors> TripError(TripException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        PatternErrors patternErrors = new PatternErrors(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(patternErrors);
    }

    @ExceptionHandler(FavoriteException.class)
    public ResponseEntity<PatternErrors> FavoriteError(FavoriteException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        PatternErrors patternErrors = new PatternErrors(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(patternErrors);
    }

}
