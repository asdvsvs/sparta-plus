package com.sparta.plus.service;

import com.sparta.plus.dto.request.MemberLoginReq;
import com.sparta.plus.dto.request.MemberSignupReq;
import com.sparta.plus.dto.response.MemberSignupRes;

public interface MemberService {

    /**
     * 회원가입
     *
     * @param memberSignupReq 회원 가입 요청정보
     */
    MemberSignupRes signup(MemberSignupReq memberSignupReq);

    /**
     * 로그인
     *
     * @param memberLoginReq 로그인 요청 정보
     * @return createToken(String memberName)
     */
    String login(MemberLoginReq memberLoginReq);
}
