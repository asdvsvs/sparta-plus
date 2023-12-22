package com.sparta.plus.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.plus.CommonTest;
import com.sparta.plus.TestConfig;
import com.sparta.plus.entity.Post;
import com.sparta.plus.entity.PostLike;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class PostLikeRepositoryTest implements CommonTest {

    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    private Post savePost;
    private Post saveAnotherPost;

    @BeforeEach
    void setUp() {
        memberRepository.save(TEST_MEMBER);
        memberRepository.save(TEST_ANOTHER_MEMBER);
        savePost = postRepository.save(TEST_POST);
        saveAnotherPost = postRepository.save(TEST_ANOTHER_POST);
    }

    @Test
    @DisplayName("게시물 좋아요 여부 조회 테스트")
    void test1() {
        //given
        PostLike postLike = postLikeRepository.save(
            PostLike.builder().post(TEST_POST).member(TEST_MEMBER).build());
        PostLike anotherPostLIke = postLikeRepository.save(
            PostLike.builder().post(TEST_POST).member(TEST_ANOTHER_MEMBER).build());
        //when
        PostLike byPostAndMember = postLikeRepository.findByPostAndMember(TEST_POST, TEST_MEMBER);
        //then
        assertThat(byPostAndMember).isEqualTo(postLike);
        assertThat(byPostAndMember).isNotEqualTo(anotherPostLIke);
    }

}