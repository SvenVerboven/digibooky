package com.hobby.digibooky.dtos;

import com.hobby.digibooky.domain.Author;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class UpdateBookDto {

    @NotEmpty(message = "Title must not be empty")
    private String title;
    private String summary;
    @Valid
    private Author author;

    public UpdateBookDto(String title, String summary, Author author) {
        this.title = title;
        this.summary = summary;
        this.author = author;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
