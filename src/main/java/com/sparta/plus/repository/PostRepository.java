package com.sparta.plus.repository;

import com.sparta.plus.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    @Query("select p from Post p left join fetch p.member")
    Page<Post> findAll(Pageable pageable);

    @Query("select p from  Post p left join fetch p.member where p.postId = :postId")
    Post findByPostId(Long postId);
}
