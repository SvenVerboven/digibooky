package com.hobby.digibooky.domain;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Isbn isbn;
    private String title;
    private String summary;
    private boolean deleted;
    private boolean borrowed;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTHOR_ID")
    private Author author;

    public Book(){}

    public Book(Isbn isbn, String title, String summary, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.summary = summary;
        this.author = author;
        this.deleted = false;
        this.borrowed = false;
    }

    public Long getId() {
        return id;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public Author getAuthor() {
        return author;
    }
}
