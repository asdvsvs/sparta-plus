package com.sparta.plus.common.validator;

import com.sparta.plus.common.Response;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ValidateReq {

    public ResponseEntity<Response> validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Response(
                Objects.requireNonNull(
                    bindingResult.getFieldErrors().stream()
                        .map((e) -> e.getField() + " : " + e.getDefaultMessage())
                        .toList().toString()), HttpStatus.BAD_REQUEST.value()));
        }
        return null;
    }
}
