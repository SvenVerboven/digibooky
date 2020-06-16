package com.hobby.digibooky.services;

import com.hobby.digibooky.domain.Author;
import com.hobby.digibooky.domain.Book;
import com.hobby.digibooky.domain.Isbn;
import com.hobby.digibooky.dtos.BookDto;
import com.hobby.digibooky.infrastructure.BookRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;
    private Author author1 = new Author(
            "Joanne",
            "Rowling");
    private Book book1 = new Book(
            new Isbn("9781234567897"),
            "Harry Potter and the Philosopher",
            "the first book",
            author1);
    private Book book2 = new Book(
            new Isbn("9782123456803"),
            "Harry Potter and the Chamber of Secrets's",
            "the second book",
            author1);

    @Test
    void getAllBooks_givenTwoBooks_thenReturnTwoBooks() {
        Mockito.when(bookRepository.findAll()).thenReturn(Lists.newArrayList(book1, book2));
        List<BookDto> books = bookService.getAllBooks();
        Assertions.assertThat(books).hasSize(2);
    }
}
