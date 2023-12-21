package com.sparta.plus.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.sparta.plus.CommonTest;
import com.sparta.plus.entity.PostLike;
import com.sparta.plus.repository.MemberRepository;
import com.sparta.plus.repository.PostLikeRepository;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.service.impl.PostLikeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostLikeServiceTest implements CommonTest {

    @InjectMocks
    private PostLikeServiceImpl postLikeService;
    @Mock
    private PostLikeRepository postLikeRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PostRepository postRepository;

    @Nested
    @DisplayName("게시물 좋아요 테스트")
    class DoLike {

        @Test
        @DisplayName("게시물 좋아요 테스트 - 성공")
        void test1() {
            //given
            given(memberRepository.findByMemberName(TEST_NAME)).willReturn(TEST_MEMBER);
            given(postRepository.findByPostId(TEST_ID)).willReturn(TEST_POST);
            given(postLikeRepository.findByPostAndMember(TEST_POST, TEST_MEMBER)).willReturn(null);

            //when
            postLikeService.savePostLike(TEST_ID, TEST_NAME);

            //then
            verify(memberRepository).findByMemberName(any());
            verify(postRepository).findByPostId(any());
            verify(postLikeRepository).findByPostAndMember(any(), any());
            verify(postLikeRepository).save(any());
        }

        @Test
        @DisplayName("게시물 좋아요 테스트 - 실패")
        void test2() {
            //given
            given(memberRepository.findByMemberName(TEST_NAME)).willReturn(TEST_MEMBER);
            given(postRepository.findByPostId(TEST_ID)).willReturn(TEST_POST);
            given(postLikeRepository.findByPostAndMember(TEST_POST, TEST_MEMBER)).willReturn(
                new PostLike());

            //when
            var exception = assertThrows(IllegalArgumentException.class,
                () -> postLikeService.savePostLike(TEST_ID, TEST_NAME));

            //then
            assertEquals("이미 좋아요를 누른 상태입니다", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("게시물 좋아요취소 테스트")
    class CancelLike {

        @Test
        @DisplayName("게시물 좋아요취소 테스트 - 성공")
        void test3() {
            //given
            given(memberRepository.findByMemberName(TEST_NAME)).willReturn(TEST_MEMBER);
            given(postRepository.findByPostId(TEST_ID)).willReturn(TEST_POST);
            given(postLikeRepository.findByPostAndMember(TEST_POST, TEST_MEMBER)).willReturn(
                new PostLike());

            //when
            postLikeService.removePostLike(TEST_ID, TEST_NAME);

            //then
            verify(memberRepository).findByMemberName(any());
            verify(postRepository).findByPostId(any());
            verify(postLikeRepository).findByPostAndMember(any(), any());
            verify(postLikeRepository).delete(any());
        }

        @Test
        @DisplayName("게시물 좋아요취소 테스트 - 실패")
        void test4() {
            //given
            given(memberRepository.findByMemberName(TEST_NAME)).willReturn(TEST_MEMBER);
            given(postRepository.findByPostId(TEST_ID)).willReturn(TEST_POST);
            given(postLikeRepository.findByPostAndMember(TEST_POST, TEST_MEMBER)).willReturn(null);

            //when
            var exception = assertThrows(IllegalArgumentException.class,
                () -> postLikeService.removePostLike(TEST_ID, TEST_NAME));

            //then
            assertEquals("좋아요를 누르지 않은 상태입니다", exception.getMessage());
        }
    }
}