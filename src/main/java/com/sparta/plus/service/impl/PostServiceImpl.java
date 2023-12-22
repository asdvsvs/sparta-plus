package com.sparta.plus.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sparta.plus.common.validator.ValidatePost;
import com.sparta.plus.dto.request.PostGetPagingReq;
import com.sparta.plus.dto.request.PostImageUploadReq;
import com.sparta.plus.dto.request.PostSaveReq;
import com.sparta.plus.dto.request.PostSearchPagingReq;
import com.sparta.plus.dto.request.PostUpdateReq;
import com.sparta.plus.dto.response.PostDetailGetRes;
import com.sparta.plus.dto.response.PostGetRes;
import com.sparta.plus.dto.response.PostSearchRes;
import com.sparta.plus.entity.Member;
import com.sparta.plus.entity.Post;
import com.sparta.plus.repository.MemberRepository;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.service.PostService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ValidatePost validatePost;
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public List<PostGetRes> getPosts(PostGetPagingReq req) {

        Sort.Direction direction = req.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, req.getSortBy());
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), sort);

        Page<Post> posts = postRepository.findAllJoinMember(pageable);
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
        Post post = getPost(postId);
        return PostDetailGetRes.builder()
            .title(post.getTitle())
            .memberName(post.getMember().getMemberName())
            .createdTime(post.getCreatedTime())
            .content(post.getContent())
            .build();
    }

    @Override
    public List<PostSearchRes> searchByContainsTitleAndMember(PostSearchPagingReq req) {

        Sort.Direction direction = req.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, req.getSortBy());
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), sort);

        Page<Post> posts = postRepository.searchByContainsTitleAndMember(req.getTitle(),
            req.getMemberName(), pageable);
        return posts.stream().map(post -> PostSearchRes.builder()
            .postId(post.getPostId())
            .title(post.getTitle())
            .memberName(post.getMember().getMemberName())
            .content(post.getContent())
            .build()).toList();
    }

    @Override
    @Transactional
    public void updatePost(PostUpdateReq req, String username) {
        Post post = getPost(req.getPostId());
        compareUsername(username, post);
        post.update(req);
    }

    @Override
    public void deletePost(Long postId, String username) {
        Post post = getPost(postId);
        compareUsername(username, post);
        postRepository.delete(post);
    }

    @Override
    @Transactional
    public void imageUpload(MultipartFile multipartFile, PostImageUploadReq req, Member member)
        throws IOException {
        Post post = getPost(req.getPostId());
        if (!Objects.equals(post.getMember().getMemberName(), member.getMemberName())) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");
        }
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        post.imageUpload(amazonS3.getUrl(bucket, originalFilename).toString());
    }

    private Post getPost(Long postId) {
        Post post = postRepository.findByPostIdFetch(postId);
        validatePost.validate(post);
        return post;
    }

    private void compareUsername(String username, Post post) {
        if (!Objects.equals(post.getMember().getMemberName(), username)) {
            throw new IllegalArgumentException("유저가 작성한 글이 아닙니다.");
        }
    }
}
