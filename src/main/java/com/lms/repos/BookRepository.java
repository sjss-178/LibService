package com.lms.repos;

import com.lms.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Search books by title
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Search books by ISBN
    Optional<Book> findByIsbn(String isbn);

    // Search books by author name
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    List<Book> findByAuthorNameContaining(@Param("authorName") String authorName);

    // Find available books
    List<Book> findByAvailableCopiesGreaterThan(Integer count);

    // Find books by category
    List<Book> findByCategory_Name(String categoryName);

    // Find books by publisher
    List<Book> findByPublisher_Name(String publisherName);

    // Find active books
    List<Book> findByActiveTrue();

    // Find books with low stock
    @Query("SELECT b FROM Book b WHERE b.availableCopies <= :threshold AND b.active = true")
    List<Book> findBooksWithLowStock(@Param("threshold") Integer threshold);
    List<Book> findByCategory_Id(Long categoryId);
    List<Book> findByAuthors_Id(Long authorId);
    List<Book> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titleKeyword, String descKeyword);
}
