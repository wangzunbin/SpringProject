package com.wangzunbin.uaa.config;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

/**
 * ClassName:QueryDslConfig
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 17:22
 */

@RequiredArgsConstructor
@Configuration
public class QueryDslConfig {

    private final EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
