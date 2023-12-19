package com.sparta.plus.repository;

import static com.sparta.plus.entity.QPost.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.plus.entity.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class PostRepositoryQueryImpl implements PostRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> searchByContainsTitleAndMember(String titleCondition,
        String memberNameCondition) {
        JPAQuery<Post> query = jpaQueryFactory.select(post)
            .from(post)
            .leftJoin(post.member).fetchJoin()
            .where(titleContains(titleCondition),
                memberNameEq(memberNameCondition)
            );
        return query.fetch();
    }

    private BooleanExpression titleContains(String titleCondition) {
        return titleCondition != null ? post.title.contains(titleCondition) : null;
    }

    private BooleanExpression memberNameEq(String memberNameCondition) {
        return memberNameCondition != null ? post.member.memberName.eq(memberNameCondition) : null;
    }
}
