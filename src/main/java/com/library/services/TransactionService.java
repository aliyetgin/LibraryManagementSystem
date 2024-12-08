package com.library.services;

import com.library.models.Transaction;
import com.library.models.Book;
import com.library.models.User;
import com.library.repositories.TransactionRepository;
import com.library.repositories.BookRepository;
import com.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // Method to get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();  // This retrieves all transactions from the repository
    }

    public boolean issueBook(int bookId, int userId) {
        // Validate if book exists and is available
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null || book.getQuantity() <= 0) {
            return false;
        }

        // Validate if user exists
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }

        // Create and save the transaction
        Transaction transaction = new Transaction();
        transaction.setBook(book);  // Set the Book object
        transaction.setUser(user);  // Set the User object
        transaction.setIssueDate(new Date());
        transaction.setDueDate(new Date(System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000))); // Due in 7 days
        transaction.setReturnDate(null);
        transaction.setFine(0);

        transactionRepository.save(transaction);

        // If successful, update book quantity
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        return true;
    }

    public boolean returnBook(int transactionId, Date returnDate) {
        // Validate transaction exists
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
        if (transaction == null) {
            return false;
        }

        // Update transaction details
        transaction.setReturnDate(returnDate);

        // Calculate fine if return date is late
        long daysLate = (returnDate.getTime() - transaction.getDueDate().getTime()) / (1000 * 60 * 60 * 24);
        transaction.setFine(Math.max(0, daysLate * 1.0)); // Example: $1 fine per late day

        transactionRepository.save(transaction);

        // If successful, update book quantity
        Book book = transaction.getBook();
        if (book != null) {
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        }

        return true;
    }
}
