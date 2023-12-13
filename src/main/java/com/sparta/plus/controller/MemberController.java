package com.sparta.plus.controller;

import com.sparta.plus.common.Response;
import com.sparta.plus.dto.request.MemberLoginReq;
import com.sparta.plus.dto.request.MemberSignupReq;
import com.sparta.plus.service.interfaces.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
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

    private static ResponseEntity<Response> validateReq(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Response(
                Objects.requireNonNull(
                    bindingResult.getFieldErrors().stream()
                        .map((e) -> e.getField() + " : " + e.getDefaultMessage())
                        .toList().toString()), HttpStatus.BAD_REQUEST.value()));
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<Response> signup(
        @Validated @ModelAttribute MemberSignupReq memberSignupReq, BindingResult bindingResult) {
        ResponseEntity<Response> reqHasError = validateReq(bindingResult);
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
        memberService.setCookie(response, token);
        return ResponseEntity.ok().body(new Response("성공", HttpStatus.OK.value()));

    }


}
