package com.hobby.digibooky.mappers;

import com.hobby.digibooky.domain.Author;
import com.hobby.digibooky.dtos.AuthorDto;

public class AuthorMapper {

    private AuthorMapper(){}

    public static AuthorDto toDto(Author author){
        return new AuthorDto(
                author.getId(),
                author.getFirstName(),
                author.getLastName());
    }
}
