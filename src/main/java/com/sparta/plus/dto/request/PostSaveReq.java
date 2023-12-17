package com.sparta.plus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostSaveReq {

    @NotBlank
    @Size(max = 500)
    private String title;

    @NotBlank
    @Size(max = 5000)
    private String content;
}
