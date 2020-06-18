package com.hobby.digibooky.api.exceptionhandler;

import com.hobby.digibooky.domain.exceptions.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger exceptionLogger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(HttpStatus.BAD_GATEWAY.getReasonPhrase());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        errors.setMessage(ex.getMessage());
        errors.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
        exceptionLogger.error(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}


