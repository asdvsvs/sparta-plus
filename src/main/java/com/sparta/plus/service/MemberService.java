package com.sparta.plus.service;

import com.sparta.plus.dto.request.MemberLoginReq;
import com.sparta.plus.dto.request.MemberSignupReq;
import com.sparta.plus.entity.Member;
import com.sparta.plus.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final Long TOKEN_TIME = 60 * 60 * 1000L;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private String secretKey = "7Iqk7YyM66W07YOA7L2U65Sp7YG065+9U3ByaW5n6rCV7J2Y7Yqc7YSw7LWc7JuQ67mI7J6F64uI64ukLg==";
    private Key key;

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

    public String login(MemberLoginReq memberLoginReq) {
        Member member = memberRepository.findByMemberName(memberLoginReq.getMemberName());
        if (!Objects.equals(memberLoginReq.getMemberName(), member.getMemberName()) ||
            !passwordEncoder.matches(memberLoginReq.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요");
        }
        return createToken(member.getMemberName());
    }

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    private String createToken(String memberName) {
        Date date = new Date();

        return BEARER_PREFIX +
            Jwts.builder()
                .setSubject(memberName) // 사용자 식별자값(ID)
//                .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                .setIssuedAt(date) // 발급일
                .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                .compact();
    }

    public void setCookie(HttpServletResponse response, String token)
        throws UnsupportedEncodingException {
        token = URLEncoder.encode(token, "utf-8")
            .replaceAll("\\+", "//"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
