package com.sparta.plus;

import com.sparta.plus.entity.Member;
import com.sparta.plus.entity.Post;

public interface CommonTest {

    String ANOTHER = "another";
    String TEST_NAME = "name";
    String TEST_PASSWORD = "password";
    String TEST_ANOTHER_PASSWORD = ANOTHER + TEST_PASSWORD;
    String TEST_TITLE = "title";
    String TEST_CONTENT = "content";
    Long TEST_ID = 1L;
    String TEST_POST_LIKE = "like";
    String TEST_POST_LIKE_CANCEL = "cancel";

    Member TEST_MEMBER = Member.builder()
        .memberName(TEST_NAME)
        .password(TEST_PASSWORD)
        .build();

    Post TEST_POST = Post.builder()
        .title(TEST_TITLE)
        .content(TEST_CONTENT)
        .build();
}
