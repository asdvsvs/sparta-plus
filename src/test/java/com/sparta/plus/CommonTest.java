package com.sparta.plus;

import com.sparta.plus.entity.Member;
import com.sparta.plus.entity.Post;

public interface CommonTest {

    String ANOTHER = "another";
    String TEST_NAME = "name";
    String TEST_PASSWORD = "password";
    String TEST_ANOTHER_PASSWORD = ANOTHER + TEST_PASSWORD;
    String TEST_TITLE = "title";
    String TEST_ANOTHER_TITLE = ANOTHER + TEST_TITLE;
    String TEST_CONTENT = "content";
    String TEST_ANOTHER_CONTENT = ANOTHER + TEST_CONTENT;
    Long TEST_ID = 1L;
    String TEST_POST_LIKE = "like";
    String TEST_POST_LIKE_CANCEL = "cancel";
    String TEST_TOKEN = "token";
    Integer TEST_PAGE = 0;
    Integer TEST_SIZE = 3;
    String TEST_SORT_BY = "postId";
    Boolean TEST_ASC = true;

    Member TEST_MEMBER = Member.builder()
        .memberName(TEST_NAME)
        .password(TEST_PASSWORD)
        .build();

    Post TEST_POST = Post.builder()
        .title(TEST_TITLE)
        .content(TEST_CONTENT)
        .member(TEST_MEMBER)
        .build();
}
