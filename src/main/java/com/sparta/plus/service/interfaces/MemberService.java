package com.sparta.plus.service.interfaces;

import com.sparta.plus.dto.request.MemberLoginReq;
import com.sparta.plus.dto.request.MemberSignupReq;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface MemberService {

    /**
     * 회원가입
     *
     * @param memberSignupReq 회원 가입 요청정보
     */
    void signup(MemberSignupReq memberSignupReq);

    /**
     * 로그인
     *
     * @param memberLoginReq 로그인 요청 정보
     * @return createToken(String memberName)
     */
    String login(MemberLoginReq memberLoginReq);

    void init();

    /**
     * 멤버 이름으로 토큰 생성
     *
     * @param memberName 멤버 이름
     * @return 토큰 생성 결과
     */
    String createToken(String memberName);

    /**
     * 쿠키 설정
     *
     * @param response 응답 객체
     * @param token    토큰 정보
     * @throws UnsupportedEncodingException 인코딩 실패 시 예외
     */
    void setCookie(HttpServletResponse response, String token) throws UnsupportedEncodingException;
}
