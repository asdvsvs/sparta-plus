package com.sparta.plus.dto.request;

import lombok.Getter;

@Getter
public class PostUpdateReq {

    private Long postId;
    private String title;
    private String content;
}
