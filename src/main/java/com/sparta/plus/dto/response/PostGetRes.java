package com.sparta.plus.dto.response;

import com.sparta.plus.common.Response;
import com.sparta.plus.entity.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostGetRes extends Response {

    private String title;
    private String nickname;
    private LocalDateTime createdTime;

    public PostGetRes(Post post) {
        this.title = post.getTitle();
        this.nickname = post.getNickname();
        this.createdTime = post.getCreatedTime();
    }
}