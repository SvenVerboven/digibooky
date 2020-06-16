package com.hobby.digibooky.domain.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long bookId){
        super(String.format("Book with id: % does not exist", bookId));
    }
}
