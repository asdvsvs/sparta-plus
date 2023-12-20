package com.sparta.plus.repository;

import com.sparta.plus.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryQuery {

    Page<Post> searchByContainsTitleAndMember(String titleCondition, String memberNameCondition,
        Pageable pageable);
}
