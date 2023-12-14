package com.sparta.plus.dto.request;

import lombok.Getter;

@Getter
public class PostGetPagingReq {

    private int page;
    private int size;
    private String sortBy;
    private boolean asc;
}
