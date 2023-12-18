package com.sparta.plus.service.impl;

import com.sparta.plus.common.validator.ValidatePost;
import com.sparta.plus.dto.request.PostGetPagingReq;
import com.sparta.plus.dto.request.PostSaveReq;
import com.sparta.plus.dto.response.PostDetailGetRes;
import com.sparta.plus.dto.response.PostGetRes;
import com.sparta.plus.dto.response.PostSearchRes;
import com.sparta.plus.entity.Post;
import com.sparta.plus.repository.MemberRepository;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.service.interfaces.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ValidatePost validatePost;

    @Override
    public List<PostGetRes> getPosts(PostGetPagingReq req) {

        Sort.Direction direction = req.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, req.getSortBy());
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), sort);

        Page<Post> posts = postRepository.findAll(pageable);
        return posts.stream().map(PostGetRes::new).toList();
    }

    @Override
    public void savePost(PostSaveReq postSaveReq, String memberName) {

        Post post = Post.builder()
            .title(postSaveReq.getTitle())
            .content(postSaveReq.getContent())
            .member(memberRepository.findByMemberName(memberName))
            .build();
        postRepository.save(post);
    }

    @Override
    public PostDetailGetRes getPostDetail(Long postId) {
        Post post = postRepository.findByPostId(postId);
        validatePost.validate(post);
        return PostDetailGetRes.builder()
            .title(post.getTitle())
            .memberName(post.getMember().getMemberName())
            .createdTime(post.getCreatedTime())
            .content(post.getContent())
            .build();
    }

    @Override
    public List<PostSearchRes> searchByContainsTitleAndMember(String title, String memberName) {
        List<Post> posts = postRepository.searchByContainsTitleAndMember(title, memberName);
        return posts.stream().map(post -> PostSearchRes.builder()
            .postId(post.getPostId())
            .title(post.getTitle())
            .memberName(post.getMember().getMemberName())
            .content(post.getContent())
            .build()).toList();
    }
}
