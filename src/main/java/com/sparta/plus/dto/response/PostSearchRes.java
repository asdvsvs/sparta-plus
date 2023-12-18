package com.sparta.plus.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostSearchRes {

    private Long postId;
    private String title;
    private String memberName;
    private String content;
}
