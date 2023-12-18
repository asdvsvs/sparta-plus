package com.sparta.plus.repository;

import com.sparta.plus.entity.Post;
import java.util.List;

public interface PostRepositoryQuery {

    List<Post> searchByContainsTitleAndMember(String title, String memberName);
}
