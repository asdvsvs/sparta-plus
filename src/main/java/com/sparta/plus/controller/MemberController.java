package com.sparta.plus.controller;

import com.sparta.plus.common.RestResponse;
import com.sparta.plus.common.security.JwtUtil;
import com.sparta.plus.common.validator.ReqValidator;
import com.sparta.plus.dto.request.MemberLoginReq;
import com.sparta.plus.dto.request.MemberSignupReq;
import com.sparta.plus.dto.response.MemberSignupRes;
import com.sparta.plus.service.MemberService;
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
    private final ReqValidator reqValidator;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<MemberSignupRes> signup(
        @Validated @ModelAttribute MemberSignupReq memberSignupReq, BindingResult bindingResult) {
        RestResponse reqHasError = reqValidator.validate(bindingResult);
        if (reqHasError != null) {
            return ResponseEntity.badRequest()
                .body(new MemberSignupRes());
        }

        return ResponseEntity.ok().body(memberService.signup(memberSignupReq));
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse> login(@RequestBody MemberLoginReq memberLoginReq,
        HttpServletResponse response) throws UnsupportedEncodingException {

        String token = memberService.login(memberLoginReq);
        jwtUtil.setCookie(response, token);
        return ResponseEntity.ok().body(new RestResponse("성공", HttpStatus.OK.value()));

    }


}
