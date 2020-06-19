package com.hobby.digibooky.services;

import com.google.common.collect.Lists;
import com.hobby.digibooky.domain.Author;
import com.hobby.digibooky.domain.Book;
import com.hobby.digibooky.domain.exceptions.AuthorNotFoundException;
import com.hobby.digibooky.domain.exceptions.BookNotFoundException;
import com.hobby.digibooky.dtos.BookDto;
import com.hobby.digibooky.dtos.CreateBookDto;
import com.hobby.digibooky.dtos.UpdateBookDto;
import com.hobby.digibooky.infrastructure.AuthorRepository;
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
    private AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookDto> getAllBooks(String isbn, String title, String firstName, String lastName) {
        if (isbn != null) {
            return getBookByIsbn(isbn);
        }
        if (title != null) {
            return getBookByTitle(title);
        }
        if (firstName != null && lastName != null) {
            return getBookByAuthorFullName(firstName, lastName);
        }
        if (firstName != null) {
            return getBookByAuthorFirstName(firstName);
        }
        if (lastName != null) {
            return getBookByAuthorLastName(lastName);
        }
        return BookMapper.toDto((List<Book>) bookRepository.findAll());
    }

    public BookDto getBookById(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException(bookId);
        }
        return BookMapper.toDto(optionalBook.get());
    }

    public BookDto saveBook(CreateBookDto createBookDto) {
        return BookMapper.toDto(bookRepository.save(BookMapper.toBook(createBookDto)));
    }

    public BookDto updateBook(Long id, UpdateBookDto updateBookDto) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(!optionalBook.isPresent()){
            throw new BookNotFoundException(id);
        }
        Book book = optionalBook.get();
        book.setTitle(updateBookDto.getTitle());
        book.setSummary(updateBookDto.getSummary());
        Optional<Author> optionalAuthor = authorRepository.findById(updateBookDto.getAuthor().getId());
        if(!optionalAuthor.isPresent()){
            throw new AuthorNotFoundException(updateBookDto.getAuthor().getId());
        }
        Author author = optionalAuthor.get();
        author.setFirstName(updateBookDto.getAuthor().getFirstName());
        author.setLastName(updateBookDto.getAuthor().getLastName());
        return BookMapper.toDto(book);
    }

    public String deleteBook(Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(!optionalBook.isPresent()){
            throw new BookNotFoundException(id);
        }
        return "Book has been deleted";
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
