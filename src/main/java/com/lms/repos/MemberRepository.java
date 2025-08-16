package com.lms.repos;

import com.lms.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // Find member by member ID (library card number)
    Optional<Member> findByMemberId(String memberId);

    // Search members by name
    List<Member> findByNameContainingIgnoreCase(String name);

    // Find member by email
    Optional<Member> findByEmail(String email);

    // Find active members
    List<Member> findByActiveTrue();

    // Find members by phone number
    Optional<Member> findByPhoneNumber(String phoneNumber);
}
