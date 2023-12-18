package com.sparta.plus.repository;

import static com.sparta.plus.entity.QPost.post;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.plus.entity.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class PostRepositoryQueryImpl implements PostRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> searchByContainsTitleAndMember(String title, String memberName) {
        JPAQuery<Post> query = jpaQueryFactory.select(post)
            .from(post)
            .leftJoin(post.member).fetchJoin()
            .where(post.title.contains(title)
                .and(post.member.memberName.eq(memberName))
            );
        return query.fetch();
    }
}
