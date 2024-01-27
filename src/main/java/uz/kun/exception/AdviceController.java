package uz.kun.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(value = {AppBadException.class})
    public ResponseEntity<?> handle(AppBadException appBadException) {
        return ResponseEntity.badRequest().body(appBadException.getMessage());
    }
    @ExceptionHandler(JwtException.class)
    private ResponseEntity<?> handle(JwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handle(RuntimeException appBadException) {
        return ResponseEntity.badRequest().body(appBadException.getMessage());
    }



}
