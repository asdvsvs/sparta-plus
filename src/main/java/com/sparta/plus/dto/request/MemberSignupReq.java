package com.sparta.plus.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignupReq {

    @NotBlank
    @Size(min = 3, message = "길이 3이상 가능")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "a~z, A~Z, 0~9만 가능")
    private String memberName;

    @NotBlank
    @Size(min = 4, message = "길이 4이상 가능")
    private String password;

    @NotBlank
    @Size(min = 4, message = "길이 4이상 가능")
    private String checkPassword;

    @NotBlank
    @Email(message = "이메일 형식만 가능")
    private String email;
}
