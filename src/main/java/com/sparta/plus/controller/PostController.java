package com.sparta.plus.controller;

import com.sparta.plus.dto.response.PostGetRes;
import com.sparta.plus.service.interfaces.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostGetRes>> getPosts() {
        return ResponseEntity.ok().body(postService.getPosts());
    }
}
