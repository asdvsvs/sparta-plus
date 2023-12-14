package com.sparta.plus.service.impl;

import com.sparta.plus.dto.response.PostGetRes;
import com.sparta.plus.entity.Post;
import com.sparta.plus.service.interfaces.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<PostGetRes> getPosts() {

        List<Post> posts = postRepository.findAll(Sort.by(Direction.DESC, "createdTime"));
        return posts.stream().map(PostGetRes::new).toList();
    }
}
