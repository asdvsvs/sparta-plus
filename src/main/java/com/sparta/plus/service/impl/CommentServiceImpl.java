package com.sparta.plus.service.impl;

import com.sparta.plus.dto.request.CommentSaveReq;
import com.sparta.plus.entity.Comment;
import com.sparta.plus.entity.Member;
import com.sparta.plus.entity.Post;
import com.sparta.plus.repository.CommentRepository;
import com.sparta.plus.repository.MemberRepository;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public void saveComment(CommentSaveReq req, String memberName) {
        Member member = memberRepository.findByMemberName(memberName);
        Post post = postRepository.findByPostId(req.getPostId());
        commentRepository.save(Comment.builder()
            .member(member)
            .post(post)
            .content(req.getContent())
            .build());
    }
}
