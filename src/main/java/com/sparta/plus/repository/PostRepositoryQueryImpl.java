package com.sparta.plus.repository;

import static com.sparta.plus.entity.QPost.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.plus.entity.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;


@RequiredArgsConstructor
public class PostRepositoryQueryImpl implements PostRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> searchByContainsTitleAndMember(String titleCondition,
        String memberNameCondition, Pageable pageable) {
        JPAQuery<Post> query = jpaQueryFactory.select(post)
            .from(post)
            .leftJoin(post.member).fetchJoin()
            .where(
                titleContains(titleCondition),
                memberNameEq(memberNameCondition)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());
        List<Post> posts = query.fetch();

        JPAQuery<Long> query2 = jpaQueryFactory.select(Wildcard.count)
            .from(post)
            .leftJoin(post.member)
            .where(
                titleContains(titleCondition),
                memberNameEq(memberNameCondition)
            );
        Long totalSize = query2.fetchFirst();
        return PageableExecutionUtils.getPage(posts, pageable, () -> totalSize);
    }

    private BooleanExpression titleContains(String titleCondition) {
        return titleCondition != null ? post.title.contains(titleCondition) : null;
    }

    private BooleanExpression memberNameEq(String memberNameCondition) {
        return memberNameCondition != null ? post.member.memberName.eq(memberNameCondition) : null;
    }
}
