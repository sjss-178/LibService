package com.lms.repos;


import com.lms.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    // Find publisher by name
    Optional<Publisher> findByName(String name);

    // Search publishers by name
    List<Publisher> findByNameContainingIgnoreCase(String name);

    // Find publishers with books
    @Query("SELECT DISTINCT p FROM Publisher p WHERE SIZE(p.books) > 0")
    List<Publisher> findPublishersWithBooks();
}