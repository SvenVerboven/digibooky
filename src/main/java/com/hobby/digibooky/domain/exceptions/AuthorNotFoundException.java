package com.hobby.digibooky.domain.exceptions;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Long id){
        super(String.format("Author with id: %s does not exist", id));
    }
}
