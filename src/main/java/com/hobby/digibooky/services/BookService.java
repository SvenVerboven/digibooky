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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAllBooks(String isbn, String title, String firstName, String lastName) {
        List<Book> books = Lists.newArrayList(bookRepository.findAll());
        if (isbn != null) {
            return BookMapper.toDto(books
                    .stream()
                    .filter(book -> Pattern.matches(isbn, book.getIsbn().getIsbnNumber()))
                    .collect(Collectors.toList()));
        }
        if(title != null){
            return BookMapper.toDto(books
                    .stream()
                    .filter(book -> Pattern.matches(title.toLowerCase().trim(), book.getTitle().toLowerCase().trim()))
                    .collect(Collectors.toList()));
        }
        if(firstName != null && lastName != null){
            String fullName = firstName.concat(lastName).toLowerCase().trim();
            return BookMapper.toDto(books
                    .stream()
                    .filter(book -> Pattern.matches(fullName,
                            book.getAuthor().getFirstName().concat(book.getAuthor().getLastName()).toLowerCase().trim()))
                    .collect(Collectors.toList()));
        }
        if(firstName != null){
            return BookMapper.toDto(books
                    .stream()
                    .filter(book -> Pattern.matches(firstName.toLowerCase().trim(),
                            book.getAuthor().getFirstName().toLowerCase().trim()))
                    .collect(Collectors.toList()));
        }
        if(lastName != null){
            return BookMapper.toDto(books
                    .stream()
                    .filter(book -> Pattern.matches(lastName.toLowerCase().trim(),
                            book.getAuthor().getLastName().toLowerCase().trim()))
                    .collect(Collectors.toList()));
        }
        return BookMapper.toDto(books);
    }

    public BookDto getBookById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            throw new BookNotFoundException(bookId);
        }
        return BookMapper.toDto(book.get());
    }
}
