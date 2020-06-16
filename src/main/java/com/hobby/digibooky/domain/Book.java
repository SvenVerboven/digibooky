package com.hobby.digibooky.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    private Long id;
    @Embedded
    private ISBN isbn;
    private String title;
    private String summary;
    private boolean deleted;
    private boolean borrowed;
}
