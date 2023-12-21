package com.sparta.plus.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.sparta.plus.CommonTest;
import com.sparta.plus.dto.request.CommentSaveReq;
import com.sparta.plus.repository.CommentRepository;
import com.sparta.plus.repository.MemberRepository;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest implements CommonTest {

    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PostRepository postRepository;


    @Test
    @DisplayName("댓글 작성 테스트")
    void test1() {
        //given
        CommentSaveReq req = CommentSaveReq.builder()
            .postId(TEST_ID).content(TEST_CONTENT).build();

        //when
        commentService.saveComment(req, TEST_NAME);

        //then
        verify(memberRepository).findByMemberName(any());
        verify(postRepository).findByPostId(any());
        verify(commentRepository).save(any());
    }
}