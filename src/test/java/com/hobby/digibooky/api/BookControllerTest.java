package com.hobby.digibooky.api;

import com.hobby.digibooky.dtos.BookDto;
import com.hobby.digibooky.services.BookService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookService bookService;
    private final String DEFAULT_BOOKS_URL = "/books";
    private String emptyBookTitle = null;
    private String emptyBookIsbn = null;
    private String emptyFirstName = null;
    private String emptyLastName = null;

    @Test
    void getAllBooks_givenTwoBooks_thenReturnTwoBooks() throws Exception {
        Mockito.when(bookService.getAllBooks(emptyBookIsbn, emptyBookTitle, emptyFirstName, emptyLastName)).thenReturn(Lists.newArrayList(new BookDto(), new BookDto()));
        mvc.perform(get(DEFAULT_BOOKS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[*]", hasSize(2)));
    }

    @Test
    void getAllBooks_givenFullIsbnWhichMatchesOneBook_thenReturnOneBook() throws Exception {
        String isbn = "9781234567897";
        Mockito.when(bookService.getAllBooks(isbn, emptyBookTitle, emptyFirstName, emptyLastName)).thenReturn(Lists.newArrayList(new BookDto()));
        mvc.perform(get(DEFAULT_BOOKS_URL + "?isbn=" + isbn))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[*]", hasSize(1)));
    }

    @Test
    void getBookById_givenBookId_thenReturnBookDtoAndStatusIs200() throws Exception {
        Long bookId = 1L;
        Mockito.when(bookService.getBookById(bookId)).thenReturn(new BookDto());
        mvc.perform(get(DEFAULT_BOOKS_URL + "/" + bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}
