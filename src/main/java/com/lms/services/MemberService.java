package com.lms.services;

import com.lms.domain.BookLoan;
import com.lms.domain.Member;

import java.util.List;

public interface MemberService {
    Member registerMember(Member member);
    Member updateMember(Long id, Member member);
    Member getMemberById(Long id);
    List<Member> getAllMembers();
    void deleteMember(Long id);
    List<BookLoan> getMemberLoanHistory(Long memberId);
}

