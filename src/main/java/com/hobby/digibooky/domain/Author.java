package com.hobby.digibooky.domain;

import javax.persistence.*;

@Entity
public class Author {

    @Id
    private Long id;
    private String firstName;
    private String lastName;

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
