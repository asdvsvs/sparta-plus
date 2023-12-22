package com.sparta.plus;

import com.sparta.plus.entity.Member;
import com.sparta.plus.entity.Post;

public interface CommonTest {

    String ANOTHER = "another";
    String TEST_NAME = "name";
    String TEST_PASSWORD = "password";
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
    String TEST_EMAIL = "abcdefg1@gmail.com";
    String TEST_ANOTHER_NAME = ANOTHER + TEST_NAME;
    String TEST_ANOTHER_PASSWORD = ANOTHER + TEST_PASSWORD;
    String TEST_ANOTHER_EMAIL = ANOTHER + TEST_EMAIL;

    Member TEST_ANOTHER_MEMBER = Member.builder()
        .memberName(TEST_ANOTHER_NAME)
        .password(TEST_ANOTHER_PASSWORD)
        .email(TEST_ANOTHER_EMAIL)
        .build();
    Post TEST_ANOTHER_POST = Post.builder()
        .title(TEST_ANOTHER_TITLE)
        .content(TEST_ANOTHER_CONTENT)
        .member(TEST_ANOTHER_MEMBER)
        .build();
    Member TEST_MEMBER = Member.builder()
        .memberName(TEST_NAME)
        .password(TEST_PASSWORD)
        .email(TEST_EMAIL)
        .build();
    Post TEST_POST = Post.builder()
        .title(TEST_TITLE)
        .content(TEST_CONTENT)
        .member(TEST_MEMBER)
        .build();
}
