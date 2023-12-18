package com.sparta.plus.common.validator;

import com.sparta.plus.common.exception.PostNotFoundException;
import com.sparta.plus.entity.Post;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidatePost {

    private final MessageSource messageSource;

    public void validate(Post post) {
        if (post == null) {
            throw new PostNotFoundException(
                messageSource.getMessage(
                    "not.found.post",
                    null,
                    "Not Found Post",
                    Locale.getDefault()
                )
            );
        }
    }

}
