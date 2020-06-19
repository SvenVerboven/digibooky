package com.hobby.digibooky.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobby.digibooky.domain.Author;
import com.hobby.digibooky.domain.Isbn;
import com.hobby.digibooky.domain.exceptions.BookNotFoundException;
import com.hobby.digibooky.dtos.BookDto;
import com.hobby.digibooky.dtos.CreateBookDto;
import com.hobby.digibooky.dtos.UpdateBookDto;
import com.hobby.digibooky.services.BookService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    private final String DEFAULT_BOOKS_URL = "/books";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookService bookService;
    @Autowired
    private ObjectMapper objectMapper;
    private CreateBookDto createBookDto;
    private UpdateBookDto updateBookDto;
    private BookDto bookDto;
    private String emptyBookTitle = null;
    private String emptyBookIsbn = null;
    private String emptyFirstName = null;
    private String emptyLastName = null;

    @BeforeEach
    void setUp() {
        createBookDto = new CreateBookDto(new Isbn("9781603093224"), "Java EE 8", "A great book about Java EE",
                new Author("Antonio", "Goncalves"));
        bookDto = new BookDto(1L, new Isbn("9781603093224"), "Java EE 8", "A great book about Java EE"
                , false, false, new Author("Antonio", "Goncalves"));
        updateBookDto = new UpdateBookDto("Java EE 8", "A great book about Java EE",
                new Author("Antonio", "Goncalves"));
    }

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
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void saveBook_givenValidInput_thenReturns201() throws Exception {
        Mockito.when(bookService.saveBook(Mockito.any(CreateBookDto.class))).thenReturn(bookDto);

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_BOOKS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Test
    void saveBook_givenNoIsbn_thenReturns400() throws Exception {
        createBookDto.setIsbn(new Isbn(""));

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_BOOKS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saveBook_givenNoTitle_thenReturns400() throws Exception {
        createBookDto.setTitle("");

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_BOOKS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saveBook_givenNoLastNameOfAuthor_thenReturns400() throws Exception {
        Author author = createBookDto.getAuthor();
        author.setLastName("");
        createBookDto.setAuthor(author);

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_BOOKS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateBook_givenValidInput_thenReturns201() throws Exception {
        long id = 1L;
        Mockito.when(bookService.updateBook(Mockito.any(Long.class), Mockito.any(UpdateBookDto.class))).thenReturn(bookDto);

        mvc.perform(MockMvcRequestBuilders.put(DEFAULT_BOOKS_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBookDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateBook_givenNoTitle_thenReturns400() throws Exception {
        long id = 1L;
        updateBookDto.setTitle("");

        mvc.perform(MockMvcRequestBuilders.put(DEFAULT_BOOKS_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBookDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBook_givenNoLastNameOfAuthor_thenReturns400() throws Exception {
        Author author = updateBookDto.getAuthor();
        author.setLastName("");
        updateBookDto.setAuthor(author);

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_BOOKS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBookDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteBook_givenValidInput_thenReturns200() throws Exception {
        long id = 1;
        String result = "Book has been deleted";
        Mockito.when(bookService.deleteBook(id)).thenReturn(result);

        mvc.perform(MockMvcRequestBuilders.delete(DEFAULT_BOOKS_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(result)));
    }

    @Test
    void deleteBook_givenInvalidId_thenReturns400() throws Exception {
        long id = 1;
        String result = "Book has been deleted";
        Mockito.when(bookService.deleteBook(id)).thenThrow(BookNotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.delete(DEFAULT_BOOKS_URL + "/" + id))
                .andExpect(status().isBadRequest());
    }
}
