package com.sparta.plus.dto.request;

import lombok.Getter;

@Getter
public class CommentSaveReq {

    private Long postId;
    private String content;
}
