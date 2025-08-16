package com.lms.repos;

import com.lms.domain.BookLoan;
import com.lms.domain.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {

    @Query("SELECT bl FROM BookLoan bl JOIN FETCH bl.member JOIN FETCH bl.book WHERE bl.member.id = :memberId")
    List<BookLoan> findByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT bl FROM BookLoan bl JOIN FETCH bl.book WHERE bl.id = :id")
    Optional<BookLoan> findByIdWithBook(@Param("id") Long id);

    @Query("SELECT bl FROM BookLoan bl JOIN FETCH bl.member JOIN FETCH bl.book WHERE bl.dueDate < :dueDate AND bl.status != :status")
    List<BookLoan> findByDueDateBeforeAndStatusNot(@Param("dueDate") LocalDateTime dueDate, @Param("status") LoanStatus status);
}