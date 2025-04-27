package com.example.demo.service;
import com.example.demo.model.Book;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
@Service
public class bookservice {
    public List<Book> getAllBooks() {
        return Arrays.asList(
                new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald"),
                new Book(2L, "1984", "George Orwell")
        );
    }
    public Book getBookById(Long id) {
        return getAllBooks().stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);}
    public List<Book> findAll() {
        return Arrays.asList();
    }
    public Book addBook(Book book) {
        return book;
    }
}
