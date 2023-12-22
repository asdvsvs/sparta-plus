package com.sparta.plus.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.plus.CommonTest;
import com.sparta.plus.TestConfig;
import com.sparta.plus.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class MemberRepositoryTest implements CommonTest {

    @Autowired
    private MemberRepository memberRepository;
    private Member saveMember;

    @BeforeEach
    void setUp() {
        saveMember = memberRepository.save(TEST_MEMBER);
    }

    @Test
    @DisplayName("멤버이름으로 멤버 정보 조회 테스트")
    void test1() {
        //given
        String memberName = saveMember.getMemberName();

        //when
        Member member = memberRepository.findByMemberName(memberName);

        //then
        assertThat(member).isEqualTo(saveMember);
    }
}