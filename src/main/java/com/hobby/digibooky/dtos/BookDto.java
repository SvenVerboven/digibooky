package com.hobby.digibooky.dtos;

import com.hobby.digibooky.domain.Author;
import com.hobby.digibooky.domain.Isbn;

public class BookDto {

    private Long id;
    private Isbn isbn;
    private String title;
    private String summary;
    private boolean deleted;
    private boolean borrowed;
    private Author author;

    public BookDto(Long id, Isbn isbn, String title, String summary, boolean deleted, boolean borrowed, Author author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.summary = summary;
        this.deleted = deleted;
        this.borrowed = borrowed;
        this.author = author;
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
