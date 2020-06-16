package com.hobby.digibooky.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.hobby.digibooky.domain.Author;
import com.hobby.digibooky.domain.Isbn;
import com.hobby.digibooky.mappers.AuthorMapper;
import com.hobby.digibooky.mappers.IsbnMapper;
import com.hobby.digibooky.services.Views;

public class BookDto {

    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private IsbnDto isbn;
    @JsonView(Views.Public.class)
    private String title;
    private String summary;
    private boolean deleted;
    private boolean borrowed;
    @JsonView(Views.Public.class)
    private AuthorDto author;

    public BookDto(Long id, Isbn isbn, String title, String summary, boolean deleted, boolean borrowed, Author author) {
        this.id = id;
        this.isbn = IsbnMapper.toDto(isbn);
        this.title = title;
        this.summary = summary;
        this.deleted = deleted;
        this.borrowed = borrowed;
        this.author = AuthorMapper.toDto(author);
    }

    public BookDto(){}

    public Long getId() {
        return id;
    }

    public IsbnDto getIsbn() {
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

    public AuthorDto getAuthor() {
        return author;
    }
}
