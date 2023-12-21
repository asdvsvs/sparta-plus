package com.sparta.plus.common.validator;

import com.sparta.plus.common.RestResponse;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ReqValidator {

    public ResponseEntity<RestResponse> validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new RestResponse(
                Objects.requireNonNull(
                    bindingResult.getFieldErrors().stream()
                        .map((e) -> e.getField() + " : " + e.getDefaultMessage())
                        .toList().toString()),
                HttpStatus.BAD_REQUEST.value()
            ));
        }
        return null;
    }
}
