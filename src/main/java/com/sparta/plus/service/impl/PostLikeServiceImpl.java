package com.sparta.plus.service.impl;

import com.sparta.plus.entity.Member;
import com.sparta.plus.entity.Post;
import com.sparta.plus.entity.PostLike;
import com.sparta.plus.repository.MemberRepository;
import com.sparta.plus.repository.PostLikeRepository;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.service.interfaces.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public void savePostLike(Long postId, String username) {
        Member member = memberRepository.findByMemberName(username);
        Post post = postRepository.findByPostId(postId);
        PostLike postLike = postLikeRepository.findByPostAndMember(post, member);
        if (postLike != null) {
            throw new IllegalArgumentException("이미 좋아요를 누른 상태입니다");
        }
        postLikeRepository.save(PostLike.builder()
            .post(post)
            .member(member)
            .build());
    }

    @Override
    public void removePostLike(Long postId, String username) {
        Member member = memberRepository.findByMemberName(username);
        Post post = postRepository.findByPostId(postId);
        PostLike postLike = postLikeRepository.findByPostAndMember(post, member);
        if (postLike == null) {
            throw new IllegalArgumentException("좋아요를 누르지 않은 상태입니다");
        }
        postLikeRepository.delete(postLike);
    }
}
