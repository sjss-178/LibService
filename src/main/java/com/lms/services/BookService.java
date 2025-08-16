package com.lms.services;

import com.lms.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    void addBook(Book book);
    Optional<Book> getBookById(Long bookId);
    void updateBook(Book book);
    void deleteBook(Long bookId);
    List<Book> getBooksByCategory(Long categoryId);
}