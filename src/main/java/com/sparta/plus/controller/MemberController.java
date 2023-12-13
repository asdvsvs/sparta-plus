package com.sparta.plus.controller;

import com.sparta.plus.common.Response;
import com.sparta.plus.dto.request.MemberLoginReq;
import com.sparta.plus.dto.request.MemberSignupReq;
import com.sparta.plus.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
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

    @PostMapping
    public ResponseEntity<Response> signup(
        @Validated @ModelAttribute MemberSignupReq memberSignupReq,
        BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(new Response(HttpStatus.BAD_REQUEST.value(),
                    Objects.requireNonNull(
                        bindingResult.getFieldErrors().stream()
                            .map((e) -> e.getField() + " : " + e.getDefaultMessage())
                            .toList().toString())));
            }
            memberService.signup(memberSignupReq);
            return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "성공"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody MemberLoginReq memberLoginReq,
        HttpServletResponse response) {
        try {
            String token = memberService.login(memberLoginReq);
            token = URLEncoder.encode(token, "utf-8")
                .replaceAll("\\+", "//"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
            Cookie cookie = new Cookie("Authorization", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "성공"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }
}
