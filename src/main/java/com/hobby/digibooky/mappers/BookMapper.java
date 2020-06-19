package com.hobby.digibooky.mappers;

import com.hobby.digibooky.domain.Book;
import com.hobby.digibooky.dtos.BookDto;
import com.hobby.digibooky.dtos.CreateBookDto;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    private BookMapper(){}

    public static BookDto toDto(Book book){
        return new BookDto(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getSummary(),
                book.isDeleted(),
                book.isBorrowed(),
                book.getAuthor());
    }

    public static List<BookDto> toDto(List<Book> books){
        return books.stream().map(BookMapper::toDto).collect(Collectors.toList());
    }

    public static Book toBook(CreateBookDto createBookDto) {
        return new Book(
                createBookDto.getIsbn(),
                createBookDto.getTitle(),
                createBookDto.getSummary(),
                createBookDto.getAuthor()
        );
    }
}
