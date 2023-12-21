package com.sparta.plus.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchPagingReq {

    private String title;
    private String memberName;
    private int page;
    private int size;
    private String sortBy;
    private boolean asc;
}
