package com.hobby.digibooky.domain.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long bookId){
        super(String.format("Book with id: %s does not exist", bookId));
    }
}
