package com.lms.services;

import com.lms.domain.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    Book updateBook(Long id, Book book);
    Book getBookById(Long id);
    List<Book> getAllBooks();
    void deleteBook(Long id);
    List<Book> getBooksByCategory(Long categoryId);
    List<Book> getBooksByAuthor(Long authorId);
    List<Book> searchBooks(String keyword);
}

