package com.hobby.digibooky.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.hobby.digibooky.services.Views;

public class IsbnDto {

    @JsonView(Views.Public.class)
    private String isbnNumber;

    public IsbnDto(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }
}
