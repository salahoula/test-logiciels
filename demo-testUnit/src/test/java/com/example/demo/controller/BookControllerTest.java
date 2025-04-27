package com.example.demo.controller;
import com.example.demo.model.Book;
import com.example.demo.service.bookservice;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private bookservice bookservice;
    @InjectMocks
    private BookController bookController;

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald"),
                new Book(2L, "1984", "George Orwell")
        );
        when(bookservice.getAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("The Great Gatsby")))
                .andExpect(jsonPath("$[1].title", is("1984")));
    }
    @Test
    public void testGetBookById_Found() throws Exception {
        Book book = new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald");
        when(bookservice.getBookById(1L)).thenReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("The Great Gatsby")))
                .andExpect(jsonPath("$.author", is("F. Scott Fitzgerald")));
    }
    @Test
    public void testGetBookById_NotFound() throws Exception {
        when(bookservice.getBookById(3L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testAddBook() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        Book newBook = new Book(3L, "Brave New World", "Aldous Huxley");
        when(bookservice.addBook(newBook)).thenReturn(newBook);
        String newBookJson = """
            {
              "id": 3,
              "title": "Brave New World",
              "author": "Aldous Huxley"
            }
            """;
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBookJson)  // Vérifie que newBookJson est une chaîne JSON valide
                        .accept("application/json"))
                .andExpect(status().isCreated())  // Attendre un statut 201
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("Brave New World"))
                .andExpect(jsonPath("$.author").value("Aldous Huxley"));

    }}
