package com.sparta.plus.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparta.plus.common.validator.ReqValidator;
import com.sparta.plus.dto.request.PostGetPagingReq;
import com.sparta.plus.dto.request.PostSaveReq;
import com.sparta.plus.dto.request.PostSearchPagingReq;
import com.sparta.plus.dto.request.PostUpdateReq;
import com.sparta.plus.dto.response.PostDetailGetRes;
import com.sparta.plus.dto.response.PostGetRes;
import com.sparta.plus.dto.response.PostSearchRes;
import com.sparta.plus.service.interfaces.PostService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = {PostController.class})
class PostControllerTest extends ControllerTest {

    @MockBean
    private PostService postService;
    @MockBean
    private ReqValidator reqValidator;

    @Test
    @DisplayName("게시물 전체 조회 테스트")
    void test1() throws Exception {
        //given
        PostGetRes res = new PostGetRes(TEST_POST);
        List<PostGetRes> result = List.of(res);
        given(postService.getPosts(any())).willReturn(result);

        //when
        ResultActions actions = mockMvc.perform(get("/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new PostGetPagingReq())));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("게시물 저장 테스트")
    void test2() throws Exception {
        //given
        PostSaveReq req = PostSaveReq.builder()
            .title(TEST_TITLE)
            .content(TEST_CONTENT)
            .build();

        //when
        ResultActions actions = mockMvc.perform(post("/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("게시물 단건 조회 테스트")
    void test3() throws Exception {
        //given
        PostDetailGetRes res = PostDetailGetRes.builder()
            .title(TEST_TITLE)
            .content(TEST_CONTENT)
            .memberName(TEST_NAME)
            .build();
        given(postService.getPostDetail(any())).willReturn(res);

        //when
        ResultActions actions = mockMvc.perform(get("/v1/posts/" + TEST_ID));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("게시물 페이징 조회 컨트롤러 테스트")
    void test4() throws Exception {
        //given
        PostSearchPagingReq req = PostSearchPagingReq.builder()
            .title(TEST_TITLE)
            .memberName(TEST_NAME)
            .build();
        PostSearchRes res = PostSearchRes.builder()
            .title(TEST_TITLE)
            .content(TEST_CONTENT)
            .memberName(TEST_NAME)
            .build();
        List<PostSearchRes> result = List.of(res);
        given(postService.searchByContainsTitleAndMember(any())).willReturn(result);

        //when
        ResultActions actions = mockMvc.perform(get("/v1/posts/search")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("게시물 수정 테스트")
    void test5() throws Exception {
        //given
        PostUpdateReq req = PostUpdateReq.builder()
            .title(TEST_TITLE)
            .content(TEST_CONTENT)
            .build();

        //when
        ResultActions actions = mockMvc.perform(patch("/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("게시물 삭제 테스트")
    void test6() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(delete("/v1/posts/" + TEST_ID));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }
}