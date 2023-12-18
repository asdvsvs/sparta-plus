package com.sparta.plus.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostDetailGetRes {

    private String title;
    private String memberName;
    private LocalDateTime createdTime;
    private String content;
}
