package com.hobby.digibooky.services;

import com.hobby.digibooky.domain.Author;
import com.hobby.digibooky.domain.Book;
import com.hobby.digibooky.domain.Isbn;
import com.hobby.digibooky.domain.exceptions.AuthorNotFoundException;
import com.hobby.digibooky.domain.exceptions.BookNotFoundException;
import com.hobby.digibooky.dtos.BookDto;
import com.hobby.digibooky.dtos.CreateBookDto;
import com.hobby.digibooky.dtos.UpdateBookDto;
import com.hobby.digibooky.infrastructure.AuthorRepository;
import com.hobby.digibooky.infrastructure.BookRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @Autowired
    private BookService bookService;
    private Author author1;
    private Book book1;
    private Book book2;
    private UpdateBookDto updateBookDto;
    private String emptyBookTitle = null;
    private String emptyBookIsbn = null;
    private String emptyFirstName = null;
    private String emptyLastName = null;

    @BeforeEach
    void setUp() {
        author1 = new Author("Joanne", "Rowling");
        book1 = new Book(
                new Isbn("9781234567897"),
                "Harry Potter and the Philosopher",
                "the first book",
                author1);
        book2 = new Book(
                new Isbn("9782123456803"),
                "Harry Potter and the Chamber of Secrets's",
                "the second book",
                author1);
        updateBookDto = new UpdateBookDto("Harry Potter and the Philosopher",
                "the first book",
                author1);
    }

    @Test
    void getAllBooks_givenTwoBooks_thenReturnsTwoBooks() {
        Mockito.when(bookRepository.findAll()).thenReturn(Lists.newArrayList(book1, book2));

        List<BookDto> books = bookService.getAllBooks(emptyBookIsbn, emptyBookTitle, emptyFirstName, emptyLastName);

        Assertions.assertThat(books).hasSize(2);
        Assertions.assertThat(books.get(0)).isInstanceOf(BookDto.class);
    }


    @Test
    void getAllBooks_givenFullIsbnWhichMatchesOneBook_thenReturnsOneBook() {
        Mockito.when(bookRepository.findAll()).thenReturn(Lists.newArrayList(book1, book2));

        List<BookDto> books = bookService.getAllBooks(book1.getIsbn().getIsbnNumber(), emptyBookTitle, emptyFirstName, emptyLastName);
        Assertions.assertThat(books).hasSize(1);
    }

    @Test
    void getAllBooks_givenIsbnWithWildcardsWhichMatchesOneBook_thenReturnsOneBook() {
        Mockito.when(bookRepository.findAll()).thenReturn(Lists.newArrayList(book1, book2));

        List<BookDto> books = bookService.getAllBooks("978.23456.*", emptyBookTitle, emptyFirstName, emptyLastName);
        Assertions.assertThat(books).hasSize(1);
    }

    @Test
    void getAllBooks_givenTitleWhichMatchesOneBook_thenReturnsOneBook() {
        Mockito.when(bookRepository.findAll()).thenReturn(Lists.newArrayList(book1, book2));

        List<BookDto> books = bookService.getAllBooks(emptyBookIsbn, book1.getTitle(), emptyFirstName, emptyLastName);
        Assertions.assertThat(books).hasSize(1);
    }

    @Test
    void getAllBooks_givenTitleWithWildcardsWhichMatchesOneBook_thenReturnOneBook() {
        Mockito.when(bookRepository.findAll()).thenReturn(Lists.newArrayList(book1, book2));

        List<BookDto> books = bookService.getAllBooks(emptyBookIsbn, ".*ch.mBer.*", emptyFirstName, emptyLastName);
        Assertions.assertThat(books).hasSize(1);
    }

    @Test
    void getBookById_givenValidInput_thenReturnsBook() {
        Mockito.when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));

        BookDto bookDto = bookService.getBookById(book1.getId());

        Assertions.assertThat(bookDto).isNotNull();
        Assertions.assertThat(bookDto).isInstanceOf(BookDto.class);
        Assertions.assertThat(bookDto.getId()).isEqualTo(book1.getId());
    }

    @Test
    void getBookById_givenWrongBookId_thenThrowBookNotFoundException() {
        Mockito.when(bookRepository.findById(book1.getId())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> bookService.getBookById(book2.getId())).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void saveBook_givenValidInput_thenReturnsBookDto() {
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book1);

        BookDto savedBook = bookService.saveBook(Mockito.mock(CreateBookDto.class));
        Assertions.assertThat(savedBook).isNotNull();
    }

    @Test
    void updateBook_givenValidInput_thenReturnsBookDto() {
        Mockito.when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));
        Mockito.when(authorRepository.findById(author1.getId())).thenReturn(Optional.of(author1));

        BookDto updatedBook = bookService.updateBook(book1.getId(), updateBookDto);

        Assertions.assertThat(updatedBook).isNotNull();
    }

    @Test
    void updateBook_givenInvalidId_thenThrowsBookNotFoundException() {
        Long invalidBookId = 100L;
        Mockito.when(bookRepository.findById(invalidBookId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()-> bookService.updateBook(invalidBookId, updateBookDto))
        .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void updateBook_givenInvalidAuthorId_thenThrowsAuthorNotFoundException() {
        long invalidAuthorId = 100;
        Mockito.when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));
        Mockito.when(authorRepository.findById(invalidAuthorId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()-> bookService.updateBook(book1.getId(), updateBookDto))
        .isInstanceOf(AuthorNotFoundException.class);
    }

    @Test
    void deleteBook_givenValidInput_thenReturnsString() {
        Mockito.when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));

        String result = bookService.deleteBook(book1.getId());

        Assertions.assertThat(result).isEqualTo("Book has been deleted");
    }

    @Test
    void deleteBook_givenInValidId_thenThrowsBookNotFoundException() {
        Mockito.when(bookRepository.findById(book1.getId())).thenReturn(Optional.empty());

       Assertions.assertThatThrownBy(()-> bookService.deleteBook(book1.getId())).isInstanceOf(BookNotFoundException.class);
    }
}
