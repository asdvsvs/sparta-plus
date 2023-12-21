package com.sparta.plus.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparta.plus.dto.request.CommentSaveReq;
import com.sparta.plus.service.interfaces.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = {CommentController.class})
class CommentControllerTest extends ControllerTest {

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("댓글 저장 테스트")
    void test1() throws Exception {
        //given
        CommentSaveReq req = CommentSaveReq.builder()
            .postId(TEST_ID)
            .content(TEST_CONTENT)
            .build();

        //when
        ResultActions actions = mockMvc.perform(post("/v1/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }
}