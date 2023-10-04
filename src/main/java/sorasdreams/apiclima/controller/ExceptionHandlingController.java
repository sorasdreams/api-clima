package sorasdreams.apiclima.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sorasdreams.apiclima.model.ExceptionResponse;

@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> resourceAccesoDenegado(Exception ex) {
        log.error("Ocurrió un error: {}", ex.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setCode("error");
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
