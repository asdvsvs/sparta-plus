package com.sparta.plus;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.plus.repository.PostRepositoryQuery;
import com.sparta.plus.repository.PostRepositoryQueryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public PostRepositoryQuery postRepositoryQuery() {
        return new PostRepositoryQueryImpl(jpaQueryFactory());
    }
}
