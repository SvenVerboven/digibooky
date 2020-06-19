package com.hobby.digibooky.dtos;

import com.hobby.digibooky.domain.Author;
import com.hobby.digibooky.domain.Isbn;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class CreateBookDto {

    @Valid
    private Isbn isbn;
    @NotEmpty(message = "Title must not be empty")
    private String title;
    private String summary;
    @Valid
    private Author author;


    public CreateBookDto(Isbn isbn, String title, String summary, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.summary = summary;
        this.author = author;
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

    public Author getAuthor() {
        return author;
    }

    public void setIsbn(Isbn isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
