package com.sparta.plus.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.plus.common.RestResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberSignupRes extends RestResponse {

    private String memberName;
    private String message;
    private Integer status;

    public MemberSignupRes() {
        this.message = super.getMessage();
        this.status = super.getStatus();
    }
}
