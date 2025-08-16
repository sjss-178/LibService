package com.lms.services;

import com.lms.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author addAuthor(Author author);
    Author updateAuthor(Long id, Author author);
    Optional<Author> getAuthorById(Long id);
    List<Author> getAllAuthors();
    void deleteAuthor(Long id);
}
