package com.hobby.digibooky.services;

import com.google.common.collect.Lists;
import com.hobby.digibooky.domain.Book;
import com.hobby.digibooky.domain.exceptions.BookNotFoundException;
import com.hobby.digibooky.dtos.BookDto;
import com.hobby.digibooky.infrastructure.BookRepository;
import com.hobby.digibooky.mappers.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAllBooks() {
        return BookMapper.toDto(Lists.newArrayList(bookRepository.findAll()));
    }

    public BookDto getBook(Long bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        if(!book.isPresent()){
            throw new BookNotFoundException(bookId);
        }
        return BookMapper.toDto(book.get());
    }
}
