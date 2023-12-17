package com.sparta.plus.controller;

import com.sparta.plus.common.Response;
import com.sparta.plus.common.exception.ValidateReq;
import com.sparta.plus.common.security.JwtUtil;
import com.sparta.plus.dto.request.MemberLoginReq;
import com.sparta.plus.dto.request.MemberSignupReq;
import com.sparta.plus.service.interfaces.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "멤버 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final ValidateReq validateReq;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Response> signup(
        @Validated @ModelAttribute MemberSignupReq memberSignupReq, BindingResult bindingResult) {
        ResponseEntity<Response> reqHasError = validateReq.validate(bindingResult);
        if (reqHasError != null) {
            return reqHasError;
        }

        memberService.signup(memberSignupReq);
        return ResponseEntity.ok().body(new Response("성공", HttpStatus.OK.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody MemberLoginReq memberLoginReq,
        HttpServletResponse response) throws UnsupportedEncodingException {

        String token = memberService.login(memberLoginReq);
        jwtUtil.setCookie(response, token);
        return ResponseEntity.ok().body(new Response("성공", HttpStatus.OK.value()));

    }


}
