package com.sparta.plus.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostGetPagingReq {

    private int page;
    private int size;
    private String sortBy;
    private boolean asc;
}
