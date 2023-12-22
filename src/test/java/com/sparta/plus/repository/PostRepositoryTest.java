package com.sparta.plus.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.plus.CommonTest;
import com.sparta.plus.TestConfig;
import com.sparta.plus.entity.Post;
import java.util.Collections;
import java.util.List;
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
class PostRepositoryTest implements CommonTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    private Post savePost;
    private Post saveAnotherPost;

    @BeforeEach
    void setUp() {
        savePost = postRepository.save(TEST_POST);
        saveAnotherPost = postRepository.save(TEST_ANOTHER_POST);
        memberRepository.save(TEST_MEMBER);
        memberRepository.save(TEST_ANOTHER_MEMBER);
    }

    @Test
    @DisplayName("게시물 전체 페이징 조회 테스트")
    void test1() {
        //given
        Pageable pageable = PageRequest
            .of(TEST_PAGE, TEST_SIZE, Sort.by(Direction.DESC, TEST_SORT_BY));
        List<Post> postList = new java.util.ArrayList<>(List.of(savePost, saveAnotherPost));
        Collections.reverse(postList);

        //when
        Page<Post> posts = postRepository.findAllJoinMember(pageable);

        //then
        //pageable의 페이지랑 사이즈 달라지면 에러날 것 같음, 나중에 고치자
        assertThat(posts.stream().map(Post::getPostId).toList().get(0)).isEqualTo(
            postList.stream().map(Post::getPostId).toList().get(0));
    }

    @Test
    @DisplayName("게시물 단건 조회(조인멤버) 테스트")
    void test2() {
        //given

        //when
        Post post = postRepository.findByPostIdFetch(TEST_ID);
        //then
        assertThat(post.getMember()).isEqualTo(savePost.getMember());
        assertThat(post.getMember()).isNotEqualTo(saveAnotherPost.getMember());
    }

    @Test
    @DisplayName("게시물 단건 조회 테스트")
    void test3() {
        //given

        //when
        Post post = postRepository.findByPostIdFetch(TEST_ID);
        //then
        assertThat(post).isEqualTo(savePost);
        assertThat(post).isNotEqualTo(saveAnotherPost);
    }
}