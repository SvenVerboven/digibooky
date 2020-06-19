package com.hobby.digibooky.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class Isbn {

    @NotEmpty(message = "Isbn must not be empty")
    private String isbnNumber;

    public Isbn(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public Isbn(){}

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }
}
