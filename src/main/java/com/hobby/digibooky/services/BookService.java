package com.hobby.digibooky.services;

import com.google.common.collect.Lists;
import com.hobby.digibooky.domain.Book;
import com.hobby.digibooky.domain.exceptions.BookNotFoundException;
import com.hobby.digibooky.dtos.BookDto;
import com.hobby.digibooky.dtos.CreateBookDto;
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

    public List<BookDto> getAllBooks(String isbn, String title, String firstName, String lastName){
        if (isbn != null) {
            return getBookByIsbn(isbn);
        }
        if(title != null){
            return getBookByTitle(title);
        }
        if(firstName != null && lastName != null){
            return getBookByAuthorFullName(firstName, lastName);
        }
        if(firstName != null){
            return getBookByAuthorFirstName(firstName);
        }
        if(lastName != null){
            return getBookByAuthorLastName(lastName);
        }
        return BookMapper.toDto((List<Book>) bookRepository.findAll());
    }

    public BookDto getBookById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            throw new BookNotFoundException(bookId);
        }
        return BookMapper.toDto(book.get());
    }

    public BookDto saveBook(CreateBookDto createBookDto){
        return BookMapper.toDto(bookRepository.save(BookMapper.toBook(createBookDto)));
    }

    private List<BookDto> getBookByAuthorLastName(String lastName) {
        List<Book> books = Lists.newArrayList(bookRepository.findAll());
        return BookMapper.toDto(books
                .stream()
                .filter(book -> Pattern.matches(lastName.toLowerCase().trim(),
                        book.getAuthor().getLastName().toLowerCase().trim()))
                .collect(Collectors.toList()));
    }

    private List<BookDto> getBookByAuthorFirstName(String firstName) {
        List<Book> books = Lists.newArrayList(bookRepository.findAll());
        return BookMapper.toDto(books
                .stream()
                .filter(book -> Pattern.matches(firstName.toLowerCase().trim(),
                        book.getAuthor().getFirstName().toLowerCase().trim()))
                .collect(Collectors.toList()));
    }

    private List<BookDto> getBookByAuthorFullName(String firstName, String lastName) {
        List<Book> books = Lists.newArrayList(bookRepository.findAll());
        String fullName = firstName.concat(lastName).toLowerCase().trim();
        return BookMapper.toDto(books
                .stream()
                .filter(book -> Pattern.matches(fullName,
                        book.getAuthor().getFirstName().concat(book.getAuthor().getLastName()).toLowerCase().trim()))
                .collect(Collectors.toList()));
    }

    private List<BookDto> getBookByTitle(String title) {
        List<Book> books = Lists.newArrayList(bookRepository.findAll());
        return BookMapper.toDto(books
                .stream()
                .filter(book -> Pattern.matches(title.toLowerCase().trim(), book.getTitle().toLowerCase().trim()))
                .collect(Collectors.toList()));
    }

    private List<BookDto> getBookByIsbn(String isbn) {
        List<Book> books = Lists.newArrayList(bookRepository.findAll());
        return BookMapper.toDto(books
                .stream()
                .filter(book -> Pattern.matches(isbn, book.getIsbn().getIsbnNumber()))
                .collect(Collectors.toList()));
    }
}
