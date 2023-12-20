package com.sparta.plus.repository;

import com.sparta.plus.entity.Member;
import com.sparta.plus.entity.Post;
import com.sparta.plus.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("select p from PostLike p "
        + "left join fetch p.member "
        + "left join fetch p.post "
        + "where p.post =:post and p.member =:member")
    PostLike findByPostAndMember(Post post, Member member);
}
