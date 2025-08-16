package com.lms.services.impl;

import com.lms.domain.Author;
import com.lms.exceptions.ResourceNotFoundException;
import com.lms.repos.AuthorRepository;
import com.lms.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;
    @Override
    public Author addAuthor(Author author) {
        authorRepository.save(author);
        return author;
    }

    @Override
    public Author updateAuthor(Long id, Author author) {
        Author existing=authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book NOT FOUND ! "));
        existing.setName(author.getName());
        existing.setBiography(author.getBiography());
        existing.setBooks(author.getBooks());
        authorRepository.save(existing);
        return existing;
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
