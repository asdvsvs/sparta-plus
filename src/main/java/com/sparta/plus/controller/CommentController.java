package com.sparta.plus.controller;

import com.sparta.plus.common.Response;
import com.sparta.plus.common.security.UserDetailsImpl;
import com.sparta.plus.dto.request.CommentSaveReq;
import com.sparta.plus.service.interfaces.CommentService;
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
@RequestMapping("/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Response> saveComment(@RequestBody CommentSaveReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.saveComment(req, userDetails.getUsername());
        return ResponseEntity.ok().body(new Response("성공", HttpStatus.OK.value()));
    }
}
