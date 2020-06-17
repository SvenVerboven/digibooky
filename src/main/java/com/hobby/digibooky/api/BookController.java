package com.hobby.digibooky.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.hobby.digibooky.dtos.BookDto;
import com.hobby.digibooky.services.BookService;
import com.hobby.digibooky.services.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Public.class)
    public List<BookDto> getAllBooks() {
        logger.info("Returned all books");
        return bookService.getAllBooks();
    }

    @GetMapping(path = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.BookDetail.class)
    public BookDto getBookById(@PathVariable Long bookId) {
            logger.info("Returned book");
            return bookService.getBookById(bookId);
    }
}
