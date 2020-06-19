package com.hobby.digibooky.api.exceptionhandler;

import com.hobby.digibooky.domain.exceptions.BookNotFoundException;
import com.hobby.digibooky.domain.exceptions.EmailNotUniqueException;
import com.hobby.digibooky.domain.exceptions.InnsNotUniqueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger exceptionLogger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> bookNotFoundExceptionHandler(BookNotFoundException ex, WebRequest request) {
        exceptionLogger.error(ex.getMessage());
        return new ResponseEntity<>(getResponseBody(ex, (ServletWebRequest) request), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgumentExceptionHandler(IllegalArgumentException ex, WebRequest request) {
        exceptionLogger.error(ex.getMessage());
        return new ResponseEntity<>(getResponseBody(ex, (ServletWebRequest) request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotUniqueException.class)
    public ResponseEntity<Object> emailNotUniqueExceptionHandler(EmailNotUniqueException ex, WebRequest request) {
        exceptionLogger.error(ex.getMessage());
        return new ResponseEntity<>(getResponseBody(ex, (ServletWebRequest) request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InnsNotUniqueException.class)
    public ResponseEntity<Object> innsNotUniqueExceptionHandler(InnsNotUniqueException ex, WebRequest request) {
        exceptionLogger.error(ex.getMessage());
        return new ResponseEntity<>(getResponseBody(ex, (ServletWebRequest) request), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", HttpStatus.BAD_GATEWAY.getReasonPhrase());
        body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("message", messages);
        messages.forEach(m -> exceptionLogger.error(m));
        return new ResponseEntity<>(body, headers, status);
    }

    private Map<String, Object> getResponseBody(Exception ex, ServletWebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_GATEWAY.getReasonPhrase());
        body.put("path", request.getRequest().getRequestURI());
        body.put("message", ex.getMessage());
        return body;
    }
}


