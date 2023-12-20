package com.sparta.plus.dto.request;

import lombok.Getter;

@Getter
public class PostSearchPagingReq {

    private String title;
    private String memberName;
    private int page;
    private int size;
    private String sortBy;
    private boolean asc;
}
