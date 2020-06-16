package com.hobby.digibooky.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Author {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    @OneToMany
    @JoinColumn(name = "AUTHOR_ID")
    private List<Book> books;
}
