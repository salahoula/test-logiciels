package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.bookservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private bookservice bookservice;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookservice.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookservice.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book createdBook = bookservice.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
}
