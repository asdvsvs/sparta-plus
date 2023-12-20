package com.sparta.plus.controller;

import com.sparta.plus.common.Response;
import com.sparta.plus.common.security.UserDetailsImpl;
import com.sparta.plus.dto.request.PostLikeReq;
import com.sparta.plus.service.interfaces.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/postLikes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<Response> doPostLike(@RequestBody PostLikeReq req,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails) {
        if (req.getWant().equals("like")) {
            postLikeService.savePostLike(req.getPostId(), userDetails.getUsername());
            return ResponseEntity.ok().body(new Response("좋아요 성공", HttpStatus.OK.value()));
        } else if (req.getWant().equals("cancel")) {
            postLikeService.removePostLike(req.getPostId(), userDetails.getUsername());
            return ResponseEntity.ok().body(new Response("취소 성공", HttpStatus.OK.value()));
        }
        return ResponseEntity.badRequest()
            .body(new Response("like, cancel 둘 중 하나만 입력하세요", HttpStatus.BAD_REQUEST.value()));
    }
}
