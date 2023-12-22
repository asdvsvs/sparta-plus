package com.sparta.plus.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.plus.CommonTest;
import com.sparta.plus.TestConfig;
import com.sparta.plus.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class PostRepositoryQueryTest implements CommonTest {

    @Autowired
    private PostRepositoryQuery postRepositoryQuery;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;


    @BeforeEach
    void setUp() {
        memberRepository.save(TEST_MEMBER);
        memberRepository.save(TEST_ANOTHER_MEMBER);
        postRepository.save(TEST_POST);
        postRepository.save(TEST_ANOTHER_POST);
    }

    @Test
    @DisplayName("제목 포함,작성자 일치 게시물 조회 테스트")
    void test1() {
        //given
        Pageable pageable = PageRequest
            .of(TEST_PAGE, TEST_SIZE, Sort.by(Direction.DESC, TEST_SORT_BY));

        //when
        Page<Post> posts = postRepositoryQuery.searchByContainsTitleAndMember(TEST_TITLE, TEST_NAME,
            pageable);

        //then
        assertThat(posts.stream().toList().get(0)).isEqualTo(TEST_POST);
        assertThat(posts.stream().toList().get(0)).isNotEqualTo(TEST_ANOTHER_POST);
    }
}