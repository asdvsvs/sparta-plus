package com.sparta.plus.repository;

import com.sparta.plus.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByMemberName(String memberName);

    Member findByMemberId(Long memberId);
}
