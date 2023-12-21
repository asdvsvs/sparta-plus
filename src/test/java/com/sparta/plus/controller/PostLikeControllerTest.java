package com.sparta.plus.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparta.plus.dto.request.PostLikeReq;
import com.sparta.plus.service.PostLikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = {PostLikeController.class})
class PostLikeControllerTest extends ControllerTest {

    @MockBean
    private PostLikeService postLikeService;

    @Nested
    @DisplayName("게시물 좋아요 테스트")
    class DoLike {

        @Test
        @DisplayName("게시물 좋아요 테스트 - 좋아요")
        void test1() throws Exception {
            //given
            PostLikeReq req = PostLikeReq.builder().postId(TEST_ID).want(TEST_POST_LIKE).build();
            //when
            ResultActions actions = mockMvc.perform(post("/v1/postLikes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)));
            //then
            actions
                .andExpect(status().isOk())
                .andDo(print());
        }

        @Test
        @DisplayName("게시물 좋아요 테스트 - 취소")
        void test2() throws Exception {
            //given
            PostLikeReq req = PostLikeReq.builder().postId(TEST_ID).want(TEST_POST_LIKE_CANCEL)
                .build();
            //when
            ResultActions actions = mockMvc.perform(post("/v1/postLikes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)));
            //then
            actions
                .andExpect(status().isOk())
                .andDo(print());
        }
    }
}