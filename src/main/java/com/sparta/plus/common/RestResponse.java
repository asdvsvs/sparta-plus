package com.sparta.plus.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse {

    protected String message;
    protected Integer status;
}
