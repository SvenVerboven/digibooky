package com.hobby.digibooky.api;

import com.hobby.digibooky.domain.exceptions.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    protected void bookNotFoundException(BookNotFoundException ex, HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
