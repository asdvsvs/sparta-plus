package com.sparta.plus.service.interfaces;

import com.sparta.plus.dto.response.PostGetRes;
import java.util.List;

public interface PostService {

    /**
     * 전체 게시물 목록 조회
     *
     * @return 전체 게시글 조화 결과
     */
    List<PostGetRes> getPosts();
}
