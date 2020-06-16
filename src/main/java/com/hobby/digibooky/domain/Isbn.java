package com.hobby.digibooky.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Isbn {

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