package com.sparta.plus.controller;

import com.sparta.plus.common.Response;
import com.sparta.plus.common.security.UserDetailsImpl;
import com.sparta.plus.common.validator.ValidateReq;
import com.sparta.plus.dto.request.PostGetPagingReq;
import com.sparta.plus.dto.request.PostSaveReq;
import com.sparta.plus.dto.request.PostSearchPagingReq;
import com.sparta.plus.dto.request.PostUpdateReq;
import com.sparta.plus.dto.response.PostDetailGetRes;
import com.sparta.plus.dto.response.PostGetRes;
import com.sparta.plus.dto.response.PostSearchRes;
import com.sparta.plus.service.interfaces.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService postService;
    private final ValidateReq validateReq;

    @GetMapping
    public ResponseEntity<List<PostGetRes>> getPosts(
        @RequestBody PostGetPagingReq postGetPagingReq) {
        return ResponseEntity.ok().body(postService.getPosts(postGetPagingReq));
    }

    @PostMapping
    public ResponseEntity<Response> savePost(@RequestBody PostSaveReq postSaveReq,
        BindingResult bindingResult,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails) {
        ResponseEntity<Response> reqHasError = validateReq.validate(bindingResult);
        if (reqHasError != null) {
            return reqHasError;
        }
        postService.savePost(postSaveReq, userDetails.getUsername());
        return ResponseEntity.ok().body(new Response("성공", HttpStatus.OK.value()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailGetRes> getPostDetail(@PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.getPostDetail(postId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostSearchRes>> searchByContainsTitleAndMember(
        @RequestBody PostSearchPagingReq req) {
        return ResponseEntity.ok()
            .body(postService.searchByContainsTitleAndMember(req));
    }

    @PatchMapping()
    public ResponseEntity<Response> updatePost(@RequestBody PostUpdateReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.updatePost(req, userDetails.getUsername());
        return ResponseEntity.ok().body(new Response("성공", HttpStatus.OK.value()));
    }
}
