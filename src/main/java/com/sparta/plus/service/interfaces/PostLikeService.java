package com.sparta.plus.service.interfaces;

public interface PostLikeService {

    void savePostLike(Long postId, String username);

    void removePostLike(Long postId, String username);
}
