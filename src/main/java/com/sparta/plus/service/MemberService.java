package com.sparta.plus.service;

import com.sparta.plus.dto.request.MemberSignupReq;
import com.sparta.plus.entity.Member;
import com.sparta.plus.repository.MemberRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(MemberSignupReq memberSignupReq) {
        
        if (Objects.equals(memberSignupReq.getMemberName(), memberSignupReq.getPassword())) {
            throw new IllegalArgumentException("이름과 동일한 비밀번호 설정 불가");
        }
        if (!Objects.equals(memberSignupReq.getPassword(), memberSignupReq.getCheckPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        if (memberRepository.findByMemberName(memberSignupReq.getMemberName()) != null) {
            throw new IllegalArgumentException("중복된 닉네임입니다");
        }
        String encodePassword = passwordEncoder.encode(memberSignupReq.getPassword());

        memberRepository.save(Member.builder()
            .memberName(memberSignupReq.getMemberName())
            .password(encodePassword)
            .email(memberSignupReq.getEmail())
            .build());
    }
}
