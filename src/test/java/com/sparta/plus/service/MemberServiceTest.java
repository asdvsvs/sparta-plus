package com.sparta.plus.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.sparta.plus.CommonTest;
import com.sparta.plus.common.security.JwtUtil;
import com.sparta.plus.dto.request.MemberLoginReq;
import com.sparta.plus.dto.request.MemberSignupReq;
import com.sparta.plus.dto.response.MemberSignupRes;
import com.sparta.plus.repository.MemberRepository;
import com.sparta.plus.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest implements CommonTest {

    @InjectMocks
    private MemberServiceImpl memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("회원가입 테스트")
    void test1() {
        //given
        MemberSignupReq req = MemberSignupReq.builder()
            .memberName(TEST_NAME)
            .password(TEST_PASSWORD)
            .checkPassword(TEST_PASSWORD)
            .build();
        given(memberRepository.save(any())).willReturn(TEST_MEMBER);

        //when
        MemberSignupRes res = memberService.signup(req);

        //then
        assertThat(res.getMemberName()).isEqualTo(TEST_NAME);
        verify(memberRepository).findByMemberName(any());
        verify(passwordEncoder).encode(any());
        verify(memberRepository).save(any());
    }

    @Test
    @DisplayName("로그인 테스트")
    void test2() {
        //given
        MemberLoginReq req = MemberLoginReq.builder()
            .memberName(TEST_NAME)
            .password(TEST_PASSWORD)
            .build();
        given(memberRepository.findByMemberName(any())).willReturn(TEST_MEMBER);
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        given(jwtUtil.createToken(any())).willReturn(TEST_TOKEN);

        //when
        String token = memberService.login(req);

        //then
        assertThat(token).isEqualTo(TEST_TOKEN);
        verify(memberRepository).findByMemberName(any());
        verify(passwordEncoder).matches(any(), any());
        verify(jwtUtil).createToken(any());
    }
}