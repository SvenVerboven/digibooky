package com.hobby.digibooky.domain.exceptions;

public class EmailNotUniqueException extends RuntimeException {
    public EmailNotUniqueException(String email){
        super(String.format("Email: %s is not unique", email));
    }
}
