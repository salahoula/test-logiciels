package com.example.demo.controller;
import com.example.demo.model.Book;
import com.example.demo.service.bookservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private bookservice bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            if (book == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Mauvaise requête si le livre est null
            }

            Book savedBook = bookService.addBook(book); // Sauvegarde du livre

            if (savedBook != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);  // Création réussie
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // En cas d'échec
            }
        } catch (Exception e) {
            // Log l'exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // Retourner une erreur générique
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            if (book == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Book savedBook = bookService.addBook(book);
            if (savedBook != null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(savedBook);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            // Log l'exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }}}


