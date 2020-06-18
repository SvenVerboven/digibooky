package com.hobby.digibooky.domain.exceptions;

public class EmailNotUnique extends RuntimeException {
    public EmailNotUnique(String email){
        super(String.format("Email: %s is not unique", email));
    }
}
