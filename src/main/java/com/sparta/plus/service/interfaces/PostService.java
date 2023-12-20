package com.sparta.plus.service.interfaces;

import com.sparta.plus.dto.request.PostGetPagingReq;
import com.sparta.plus.dto.request.PostSaveReq;
import com.sparta.plus.dto.request.PostSearchPagingReq;
import com.sparta.plus.dto.request.PostUpdateReq;
import com.sparta.plus.dto.response.PostDetailGetRes;
import com.sparta.plus.dto.response.PostGetRes;
import com.sparta.plus.dto.response.PostSearchRes;
import java.util.List;

public interface PostService {

    /**
     * 전체 게시물 목록 조회
     *
     * @return 전체 게시글 조화 결과
     */
    List<PostGetRes> getPosts(PostGetPagingReq postGetPagingReq);

    void savePost(PostSaveReq postSaveReq, String memberNames);

    PostDetailGetRes getPostDetail(Long postId);

    List<PostSearchRes> searchByContainsTitleAndMember(PostSearchPagingReq req);

    void updatePost(PostUpdateReq req, String username);

    void deletePost(Long postId, String username);
}
