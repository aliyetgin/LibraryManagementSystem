package com.library.controllers;

import com.library.models.Transaction;
import com.library.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Get all transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // Issue a book to a user
    @PostMapping("/issue")
    public String issueBook(@RequestParam("bookId") int bookId, @RequestParam("userId") int userId) {
        if (transactionService.issueBook(bookId, userId)) {
            return "Book issued successfully!";
        } else {
            return "Error: Could not issue the book!";
        }
    }

    // Return a book
    @PostMapping("/return/{transactionId}")
    public String returnBook(@PathVariable("transactionId") int transactionId, @RequestParam("returnDate") Date returnDate) {
        if (transactionService.returnBook(transactionId, returnDate)) {
            return "Book returned successfully!";
        } else {
            return "Error: Could not return the book!";
        }
    }
}
