package com.sparta.plus.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparta.plus.common.security.JwtUtil;
import com.sparta.plus.common.validator.ReqValidator;
import com.sparta.plus.dto.request.MemberLoginReq;
import com.sparta.plus.dto.request.MemberSignupReq;
import com.sparta.plus.dto.response.MemberSignupRes;
import com.sparta.plus.service.interfaces.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = {MemberController.class})
class MemberControllerTest extends ControllerTest {


    @MockBean
    private MemberService memberService;
    @MockBean
    private ReqValidator reqValidator;
    @MockBean
    private JwtUtil jwtUtil;


    @Test
    @DisplayName("회원가입 테스트")
    void test1() throws Exception {
        //given
        MemberSignupReq req = MemberSignupReq.builder().memberName(TEST_NAME).build();
        MemberSignupRes result = MemberSignupRes.builder().memberName(TEST_NAME).build();
        given(memberService.signup(any())).willReturn(result);

        //when
        ResultActions actions = mockMvc.perform(post("/v1/members")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("로그인 테스트")
    void test2() throws Exception {
        //given
        MemberLoginReq req = MemberLoginReq.builder()
            .memberName(TEST_NAME)
            .password(TEST_PASSWORD)
            .build();

        //when
        ResultActions actions = mockMvc.perform(post("/v1/members/login")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());

    }

}