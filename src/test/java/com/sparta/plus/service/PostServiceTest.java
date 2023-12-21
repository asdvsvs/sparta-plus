package com.sparta.plus.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.sparta.plus.CommonTest;
import com.sparta.plus.common.validator.ValidatePost;
import com.sparta.plus.dto.request.PostGetPagingReq;
import com.sparta.plus.dto.request.PostSaveReq;
import com.sparta.plus.dto.request.PostSearchPagingReq;
import com.sparta.plus.dto.request.PostUpdateReq;
import com.sparta.plus.dto.response.PostDetailGetRes;
import com.sparta.plus.dto.response.PostGetRes;
import com.sparta.plus.dto.response.PostSearchRes;
import com.sparta.plus.entity.Post;
import com.sparta.plus.repository.MemberRepository;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.service.impl.PostServiceImpl;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
class PostServiceTest implements CommonTest {

    @InjectMocks
    private PostServiceImpl postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ValidatePost validatePost;

    @Test
    @DisplayName("게시물 전체 조회 테스트")
    void test1() {
        //given
        PostGetPagingReq req = PostGetPagingReq.builder()
            .asc(TEST_ASC).page(TEST_PAGE).size(TEST_SIZE).sortBy(TEST_SORT_BY)
            .build();
        Page<Post> posts = new PageImpl<>(List.of(TEST_POST));
        given(postRepository.findAllJoinMember(any())).willReturn(posts);

        //when
        List<PostGetRes> res = postService.getPosts(req);

        //then
        assertThat(res.get(0).getTitle()).isEqualTo(TEST_POST.getTitle());
        verify(postRepository).findAllJoinMember(any());
    }

    @Test
    @DisplayName("게시물 저장 테스트")
    void test2() {
        //given
        PostSaveReq req = PostSaveReq.builder()
            .title(TEST_TITLE).content(TEST_CONTENT).build();

        //when
        postService.savePost(req, TEST_NAME);

        //then
        verify(memberRepository).findByMemberName(any());
        verify(postRepository).save(any());
    }

    @Test
    @DisplayName("게시물 단건 조회 테스트")
    void test3() {
        //given
        given(postRepository.findByPostIdFetch(TEST_ID)).willReturn(TEST_POST);

        //when
        PostDetailGetRes res = postService.getPostDetail(TEST_ID);

        //then
        assertThat(res.getTitle()).isEqualTo(TEST_POST.getTitle());
        verify(postRepository).findByPostIdFetch(any());
        verify(validatePost).validate(any());
    }

    @Test
    @DisplayName("게시물 페이징 조회 테스트")
    void test4() {
        //given
        PostSearchPagingReq req = PostSearchPagingReq.builder()
            .title(TEST_TITLE).memberName(TEST_NAME)
            .asc(TEST_ASC).page(TEST_PAGE).size(TEST_SIZE).sortBy(TEST_SORT_BY).build();
        Page<Post> posts = new PageImpl<>(List.of(TEST_POST));
        given(postRepository.searchByContainsTitleAndMember(any(), any(), any())).willReturn(posts);

        //when
        List<PostSearchRes> res = postService.searchByContainsTitleAndMember(req);

        //then
        assertThat(res.get(0).getTitle()).isEqualTo(req.getTitle());
        verify(postRepository).searchByContainsTitleAndMember(any(), any(), any());
    }

    @Test
    @DisplayName("게시물 수정 테스트")
    void test5() {
        //given
        PostUpdateReq req = PostUpdateReq.builder()
            .postId(TEST_ID).title(TEST_ANOTHER_TITLE).content(TEST_ANOTHER_CONTENT).build();
        given(postRepository.findByPostIdFetch(TEST_ID)).willReturn(TEST_POST);

        //when
        postService.updatePost(req, TEST_NAME);

        //then
        assertThat(TEST_POST.getTitle()).isEqualTo(req.getTitle());
        verify(postRepository).findByPostIdFetch(any());
    }

    @Test
    @DisplayName("게시물 삭제 테스트")
    void test6() {
        //given
        given(postRepository.findByPostIdFetch(TEST_ID)).willReturn(TEST_POST);

        //when
        postService.deletePost(TEST_ID, TEST_NAME);

        //then
        verify(postRepository).findByPostIdFetch(any());
        verify(postRepository).delete(any());
    }
}