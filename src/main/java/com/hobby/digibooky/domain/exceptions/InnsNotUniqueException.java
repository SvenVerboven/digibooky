package com.hobby.digibooky.domain.exceptions;

public class InnsNotUniqueException extends RuntimeException{
    public InnsNotUniqueException(String inns){
        super(String.format("Inns number: %s is not unique", inns));
    }
}
