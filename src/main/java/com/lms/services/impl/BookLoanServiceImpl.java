package com.lms.services.impl;

import com.lms.domain.Book;
import com.lms.domain.BookLoan;
import com.lms.domain.LoanStatus;
import com.lms.domain.Member;
import com.lms.exceptions.ResourceNotFoundException;
import com.lms.repos.BookLoanRepository;
import com.lms.repos.BookRepository;
import com.lms.repos.MemberRepository;
import com.lms.services.BookLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookLoanServiceImpl implements BookLoanService {

    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public BookLoan issueBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No copies available for book: " + book.getTitle());
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        BookLoan loan = BookLoan.builder()
                .book(book)
                .member(member)
                .loanDate(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusWeeks(2))
                .status(LoanStatus.ISSUED)
                .build();

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        return bookLoanRepository.save(loan);
    }

    @Override
    @Transactional
    public BookLoan returnBook(Long loanId) {
        BookLoan loan = bookLoanRepository.findByIdWithBook(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + loanId));

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new IllegalStateException("This book has already been returned");
        }

        loan.setReturnDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return bookLoanRepository.save(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookLoan> getLoansByMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found with id: " + memberId);
        }
        return bookLoanRepository.findByMemberId(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookLoan> getOverdueLoans() {
        return bookLoanRepository.findByDueDateBeforeAndStatusNot(
                LocalDateTime.now(), LoanStatus.RETURNED
        );
    }

    @Override
    public List<BookLoan> getAllLoans() {
        return bookLoanRepository.findAll();
    }
}