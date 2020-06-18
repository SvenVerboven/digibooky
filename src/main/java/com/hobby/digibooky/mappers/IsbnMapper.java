package com.hobby.digibooky.mappers;

import com.hobby.digibooky.domain.Isbn;
import com.hobby.digibooky.dtos.IsbnDto;

public class IsbnMapper {

    private IsbnMapper(){}

    public static IsbnDto toDto(Isbn isbn){
        return new IsbnDto(isbn.getIsbnNumber());
    }
}
