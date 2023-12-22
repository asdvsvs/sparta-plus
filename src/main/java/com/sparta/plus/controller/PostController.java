package com.sparta.plus.controller;

import com.sparta.plus.common.RestResponse;
import com.sparta.plus.common.security.UserDetailsImpl;
import com.sparta.plus.common.validator.ReqValidator;
import com.sparta.plus.dto.request.PostGetPagingReq;
import com.sparta.plus.dto.request.PostImageUploadReq;
import com.sparta.plus.dto.request.PostSaveReq;
import com.sparta.plus.dto.request.PostSearchPagingReq;
import com.sparta.plus.dto.request.PostUpdateReq;
import com.sparta.plus.dto.response.PostDetailGetRes;
import com.sparta.plus.dto.response.PostGetRes;
import com.sparta.plus.dto.response.PostSearchRes;
import com.sparta.plus.service.PostService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService postService;
    private final ReqValidator reqValidator;

    @GetMapping
    public ResponseEntity<List<PostGetRes>> getPosts(
        @RequestBody PostGetPagingReq postGetPagingReq) {
        return ResponseEntity.ok().body(postService.getPosts(postGetPagingReq));
    }

    @PostMapping
    public ResponseEntity<RestResponse> savePost(@RequestBody PostSaveReq postSaveReq,
        BindingResult bindingResult,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails) {
        RestResponse reqHasError = reqValidator.validate(bindingResult);
        if (reqHasError != null) {
            return ResponseEntity.badRequest().body(reqHasError);
        }
        postService.savePost(postSaveReq, userDetails.getUsername());
        return ResponseEntity.ok().body(new RestResponse("성공", HttpStatus.OK.value()));
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
    public ResponseEntity<RestResponse> updatePost(@RequestBody PostUpdateReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.updatePost(req, userDetails.getUsername());
        return ResponseEntity.ok().body(new RestResponse("성공", HttpStatus.OK.value()));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<RestResponse> deletePost(@PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUsername());
        return ResponseEntity.ok().body(new RestResponse("성공", HttpStatus.OK.value()));
    }

    @PostMapping("/imageUpload")
    public ResponseEntity<RestResponse> imageUpload(@RequestPart MultipartFile multipartFile,
        @RequestPart PostImageUploadReq req, @AuthenticationPrincipal UserDetailsImpl userDetails)
        throws IOException {
        postService.imageUpload(multipartFile, req, userDetails.getMember());
        return ResponseEntity.ok().body(new RestResponse("성공", HttpStatus.OK.value()));
    }
}
