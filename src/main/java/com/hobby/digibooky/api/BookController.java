package com.hobby.digibooky.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.hobby.digibooky.dtos.BookDto;
import com.hobby.digibooky.dtos.CreateBookDto;
import com.hobby.digibooky.dtos.UpdateBookDto;
import com.hobby.digibooky.services.BookService;
import com.hobby.digibooky.services.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final Logger bookControllerLogger = LoggerFactory.getLogger(BookController.class);
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Public.class)
    public List<BookDto> getAllBooks(@RequestParam(required = false) String isbn,
                                     @RequestParam(required = false) String title,
                                     @RequestParam(required = false) String firstName,
                                     @RequestParam(required = false) String lastName) {
        bookControllerLogger.info("Returned all books");
        return bookService.getAllBooks(isbn, title, firstName, lastName);
    }

    @GetMapping(path = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.BookDetail.class)
    public BookDto getBookById(@PathVariable Long bookId) {
        bookControllerLogger.info("Returned book");
        return bookService.getBookById(bookId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto saveBook(@Valid @RequestBody CreateBookDto createBookDto) {
        bookControllerLogger.info("Saved book");
        return bookService.saveBook(createBookDto);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto updateBook(@PathVariable Long id, @Valid @RequestBody UpdateBookDto updateBookDto) {
        bookControllerLogger.info("Updated book");
        return bookService.updateBook(id, updateBookDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteBook(@PathVariable Long id){
        bookControllerLogger.info("Deleted book");
        return bookService.deleteBook(id);
    }
}
