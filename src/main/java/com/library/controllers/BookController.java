package com.library.controllers;

import com.library.models.Book;
import com.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Get a book by its ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") int id) {
        return bookService.getBookById(id);
    }

    // Search books by title and/or author
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String author) {
        return bookService.searchBooks(title, author);
    }

    // Add a new book
    @PostMapping
    public String addBook(@RequestBody Book book) {
        if (bookService.addBook(book)) {
            return "Book added successfully!";
        } else {
            return "Error: Book could not be added!";
        }
    }

    // Update book quantity
    @PutMapping("/{id}/quantity")
    public String updateBookQuantity(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        if (bookService.updateBookQuantity(id, quantity)) {
            return "Book quantity updated successfully!";
        } else {
            return "Error: Unable to update book quantity!";
        }
    }
}
