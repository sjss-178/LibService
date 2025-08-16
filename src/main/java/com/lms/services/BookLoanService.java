package com.lms.services;

import com.lms.domain.BookLoan;

import java.util.List;

public interface BookLoanService {
    BookLoan issueBook(Long bookId, Long memberId);
    BookLoan returnBook(Long loanId);
    List<BookLoan> getLoansByMember(Long memberId);
    List<BookLoan> getOverdueLoans();
    List<BookLoan> getAllLoans();
}