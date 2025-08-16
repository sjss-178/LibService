package com.lms.repos;

import com.lms.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Find author by name
    List<Author> findByNameContainingIgnoreCase(String name);

    // Find author by exact name
    Optional<Author> findByName(String name);

    // Find authors with books
    @Query("SELECT DISTINCT a FROM Author a WHERE SIZE(a.books) > 0")
    List<Author> findAuthorsWithBooks();

    void deleteById(Long id);


}

