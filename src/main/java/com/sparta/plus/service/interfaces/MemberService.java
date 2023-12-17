package com.sparta.plus.service.interfaces;

import com.sparta.plus.dto.request.MemberLoginReq;
import com.sparta.plus.dto.request.MemberSignupReq;

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
}
